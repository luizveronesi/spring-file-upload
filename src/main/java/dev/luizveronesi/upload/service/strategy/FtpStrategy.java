package dev.luizveronesi.upload.service.strategy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.luizveronesi.upload.model.UploadRequest;
import dev.luizveronesi.upload.model.UploadResponse;
import dev.luizveronesi.upload.model.UploadType;

@Service
public class FtpStrategy implements UploadStrategy {

	@Value("${app.ftp.port:}")
	private Integer FTP_PORT;

	@Value("${app.ftp.address:}")
	private String FTP_ADDRESS;

	@Value("${app.ftp.username:}")
	private String FTP_USERNAME;

	@Value("${app.ftp.password:}")
	private String FTP_PASSWORD;

	public String upload(UploadRequest request) {
		var filename = request.getFilename();
		var ftp = new FTPClient();

		/**
		 * A super try/catch is not recommended, but I think it is best to guarantee
		 * that the server will be disconnected by the end.
		 */
		try {
			if (FTP_PORT != null)
				ftp.connect(FTP_ADDRESS, FTP_PORT);
			else
				ftp.connect(FTP_ADDRESS);

			ftp.login(FTP_USERNAME, FTP_PASSWORD);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			int reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new RuntimeException("ftp server refused connection.");
			}

			boolean folderExists = ftp.changeWorkingDirectory(request.getFolder());
			if (!folderExists) {
				ftp.makeDirectory(request.getFolder());
			}

			ftp.changeWorkingDirectory(request.getFolder());
			ftp.enterLocalPassiveMode();
			ftp.enterLocalActiveMode();
			ftp.storeFile(filename, new ByteArrayInputStream(request.getBytes()));
			ftp.logout();

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}

		return request.getFolder() + File.separator + filename;
	}

	@Override
	public UploadType getStrategyName() {
		return UploadType.FTP;
	}
}
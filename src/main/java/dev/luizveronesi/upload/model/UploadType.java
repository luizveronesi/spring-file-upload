package dev.luizveronesi.upload.model;

public enum UploadType {
	LOCAL, FTP, AWS, AZURE;
	
	public String getName() {
		return this.name();
	}
}

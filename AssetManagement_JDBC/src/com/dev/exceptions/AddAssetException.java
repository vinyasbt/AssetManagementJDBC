package com.dev.exceptions;

public class AddAssetException extends RuntimeException {
	
	public String getMessage() {
		return "asset is already present";
	}
}

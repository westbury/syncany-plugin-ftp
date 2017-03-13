/*
 * Syncany, www.syncany.org
 * Copyright (C) 2011-2014 Philipp C. Heckel <philipp.heckel@gmail.com> 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.syncany.plugins.ftp;

import org.syncany.api.transfer.ICache;
import org.syncany.api.transfer.PropertyVisitor;
import org.syncany.api.transfer.StorageException;
import org.syncany.api.transfer.TransferManager;
import org.syncany.api.transfer.TransferSettings;

/**
 * The FTP connection represents the settings required to connect to an
 * FTP-based storage backend. It can be used to initialize/create an 
 * {@link FtpTransferManager} and is part of the {@link FtpTransferPlugin}.  
 *
 * @author Philipp C. Heckel <philipp.heckel@gmail.com>
 */
public class FtpTransferSettings implements TransferSettings {
	private String hostname;
	
	private String username;
	
	private String password;
	
	private String path;
	
	private int port = 21;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return FtpTransferSettings.class.getSimpleName() + "[hostname=" + hostname + ":" + port + ", username=" + username + ", path=" + path + "]";
	}

	@Override
	public void visitProperties(PropertyVisitor visitor) {
		visitor.stringProperty("hostname", "Host Name", true, false, true, false, true, this::getHostname, this::setHostname);
		visitor.stringProperty("username", "User Name", true, false, true, false, true, this::getUsername, this::setUsername);
		visitor.stringProperty("password", "Password", true, true, true, false, true, this::getPassword, this::setPassword);
		visitor.stringProperty("path", "Relative path", true, false, true, false, true, this::getPath, this::setPath);
		visitor.integerProperty("port", "Port", true, false, true, false, true, this::getPort, this::setPort);
	}

	@Override
	public TransferManager createTransferManager(ICache cache) throws StorageException {
		if (!isValid()) {
			throw new StorageException("invalid settings");
		}
		return new FtpTransferManager(hostname, username, password, path, port, cache);
	}

	@Override
	public String getType() {
		return "ftp";
	}

	@Override
	public boolean isValid() {
		return hostname != null && path != null;
	}

	@Override
	public String getReasonForLastValidationFail() {
		// TODO Auto-generated method stub
		return null;
	}
}

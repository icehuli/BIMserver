package org.bimserver.servlets;

/******************************************************************************
 * Copyright (C) 2009-2013  BIMserver.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/

import java.io.Reader;
import java.util.GregorianCalendar;

import org.apache.commons.io.output.NullWriter;
import org.bimserver.BimServer;
import org.bimserver.endpoints.EndPoint;
import org.bimserver.models.log.AccessMethod;
import org.bimserver.shared.exceptions.ServerException;
import org.bimserver.shared.exceptions.UserException;
import org.bimserver.shared.interfaces.bimsie1.Bimsie1NotificationInterface;
import org.bimserver.shared.interfaces.bimsie1.Bimsie1RemoteServiceInterface;
import org.bimserver.webservices.ServiceMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;

public class Streamer implements EndPoint {
	private long uoid;
	private long endpointid;
	private BimServer bimServer;
	private Bimsie1NotificationInterface notificationInterface;
	private Bimsie1RemoteServiceInterface remoteServiceInterface;
	private StreamingSocketInterface streamingSocketInterface;

	public Streamer(StreamingSocketInterface streamingSocketInterface, BimServer bimServer) {
		this.streamingSocketInterface = streamingSocketInterface;
		this.bimServer = bimServer;
		notificationInterface = bimServer.getReflectorFactory().createReflector(Bimsie1NotificationInterface.class, new JsonWebsocketReflector(bimServer.getServicesMap(), streamingSocketInterface));
		remoteServiceInterface = bimServer.getReflectorFactory().createReflector(Bimsie1RemoteServiceInterface.class, new JsonWebsocketReflector(bimServer.getServicesMap(), streamingSocketInterface));
	}

	public void onOpen() {
		JsonObject welcome = new JsonObject();
		welcome.add("welcome", new JsonPrimitive(new GregorianCalendar().getTimeInMillis()));
		streamingSocketInterface.send(welcome);
	}
	
	public void onText(Reader reader) {
		JsonReader jsonreader = new JsonReader(reader);
		JsonParser parser = new JsonParser();
		JsonObject request = (JsonObject) parser.parse(jsonreader);
		if (request.has("token")) {
			String token = request.get("token").getAsString();
			try {
				ServiceMap serviceMap = bimServer.getServiceFactory().get(token, AccessMethod.JSON);
				uoid = serviceMap.getBimServerAuthInterface().getLoggedInUser().getOid();

				this.endpointid = bimServer.getEndPointManager().register(this);
				
				JsonObject enpointMessage = new JsonObject();
				enpointMessage.add("endpointid", new JsonPrimitive(endpointid));
				streamingSocketInterface.send(enpointMessage);
			} catch (UserException e) {
				e.printStackTrace();
			} catch (ServerException e) {
				e.printStackTrace();
			}
		} else {
			bimServer.getJsonHandler().execute(request, null, new NullWriter());
		}
	}

	@Override
	public void cleanup() {
		bimServer.getEndPointManager().unregister(endpointid);
	}

	public void onClose() {
		bimServer.getEndPointManager().unregister(this);
	}

	@Override
	public long getEndPointId() {
		return endpointid;
	}

	@Override
	public Bimsie1NotificationInterface getNotificationInterface() {
		return notificationInterface;
	}

	@Override
	public Bimsie1RemoteServiceInterface getRemoteServiceInterface() {
		return remoteServiceInterface;
	}

	@Override
	public long getUoid() {
		return uoid;
	}
}
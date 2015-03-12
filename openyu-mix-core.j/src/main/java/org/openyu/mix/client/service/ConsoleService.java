package org.openyu.mix.client.service;

import org.openyu.socklet.connector.frame.ClientFrame;
import org.openyu.socklet.connector.service.ClientService;

/**
 * mix console客戶端服務
 */
public interface ConsoleService extends ClientService
{

	ClientFrame getClientFrame();

	void setClientFrame(ClientFrame clientFrame);

}

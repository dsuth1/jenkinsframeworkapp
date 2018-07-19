package com.securepaas.demo.parking.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class NotificationWindow
{
    private static int notifDelay=3000;
	
	public static void showError(String message)
	{
		showFloatingMessage("Error",message,Type.ERROR_MESSAGE);
	}
	
	public static void showError(String message,Page pageToShow)
	{
		showFloatingMessage("Error",message,Type.ERROR_MESSAGE,pageToShow);
	}
	
	public static void showWarning(String message)
	{
		showFloatingMessage("Warning",message,Type.WARNING_MESSAGE);
	}
	
	public static void showWarning(String message,Page pageToShow)
	{
		showFloatingMessage("Warning",message,Type.WARNING_MESSAGE,pageToShow);
	}
	
	@SuppressWarnings("rawtypes")
	public static void showError(Exception e,Class origOfError)
	{
		String message=e.getMessage();	
		Logger systemLogger=Logger.getLogger(origOfError.getCanonicalName());
		systemLogger.log(Level.SEVERE,message,e);
		
		showFloatingMessage("Error",message,Type.ERROR_MESSAGE);
	}
	
	@SuppressWarnings("rawtypes")
	public static void showError(Exception e,Class origOfError,Page pageToShow)
	{
		String message=e.getMessage();	
		Logger systemLogger=Logger.getLogger(origOfError.getCanonicalName());
		systemLogger.log(Level.SEVERE,message,e);
		
		showFloatingMessage("Error",message,Type.ERROR_MESSAGE,pageToShow);
	}
	
	public static void showSuccess(String message)
	{
		showFloatingMessage("Success",message,Type.HUMANIZED_MESSAGE);
	}
	
	public static void showSuccess(String message,Page pageToShow)
	{
		showFloatingMessage("Success",message,Type.HUMANIZED_MESSAGE,pageToShow);
	}
	
	public static void showAccessDenied(String message)
	{
		showFloatingMessage("Access Denied",message,Type.ERROR_MESSAGE);
	}
	
	public static void showAccessDenied(String message,Page pageToShow)
	{
		showFloatingMessage("Access Denied",message,Type.ERROR_MESSAGE,pageToShow);
	}
	
	public static void showSuccessBar(String message)
	{
		showBar(message,"bar success small",Page.getCurrent());
	}
	
	public static void showErrorBar(String message)
	{
		showBar(message,"bar error small",Page.getCurrent());
	}
	
	public static void showWarningBar(String message)
	{
		showBar(message,"bar warn small",Page.getCurrent());
	}
	
	public static void showWarningBar(String message, Page pageToShow)
	{
		showBar(message,"bar warn small",pageToShow);
	}
	
	private static void showBar(String message, String style, Page pageToShow)
	{
		Notification notifWindow = new Notification(message);
		notifWindow.setDelayMsec(notifDelay);
        notifWindow.setPosition(Position.BOTTOM_CENTER);
        notifWindow.setStyleName(style.toString());
    	notifWindow.show(pageToShow);
	}
		
	private static void showFloatingMessage(String caption, String description, Type messageType)
	{
		showFloatingMessage(caption,description,messageType,Page.getCurrent());
	}
	
	private static void showFloatingMessage(String caption, String description, Type messageType, Page pageToShow)
	{
		Notification notifWindow = new Notification("<center>"+caption+"<br/>",description+"</center>",messageType,true);
		notifWindow.setPosition(Position.MIDDLE_CENTER);     
    	notifWindow.setDelayMsec(notifDelay);
    	notifWindow.show(pageToShow);
	}
	

	
	
	
	
}

package com.github.adminfaces.starter.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Notification  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	@Enumerated(EnumType.STRING)
	private TypeNotification type_notification ;

	@Temporal (TemporalType.DATE)
	private Date send_date=new Date() ;
	
	@Column(name="message")
	private String message ;
	@Column(name="senton")
	private String senton ;
	
	
	@ManyToOne 
	@JsonIgnore
	Client clientNotif;
	
	
	public String getSenton() {
		return senton;
	}

	public void setSenton(String senton) {
		this.senton = senton;
	}

	public Client getClientNotif() {
		return clientNotif;
	}

	public void setClientNotif(Client clientNotif) {
		this.clientNotif = clientNotif;
	}

	public Notification() {
		super();
	}
	
	public String getsenton() {
		return senton;
	}

	public void setsenton(String senton) {
		this.senton = senton;
	}

	public Integer getId() {
		return id;
	}

	public TypeNotification getType_notification() {
		return type_notification;
	}

	public void setType_notification(TypeNotification type_notification) {
		this.type_notification = type_notification;
	}
	
	
	public Date getSend_date() {
		return send_date;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	public String getMessage() {
		return message;
	}

	public void setDesc_message(String desc_message) {
		this.message = desc_message;
	}



	public void setId(Integer id_notification) {
		this.id = id_notification;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		if (send_date == null) {
			if (other.send_date != null)
				return false;
		} else if (!send_date.equals(other.send_date))
			return false;
		if (send_date == null) {
			if (other.send_date != null)
				return false;
		} else if (!send_date.equals(other.send_date))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Notification [id_notification=" + id + ", type_notification=" + type_notification + ", message=" + message + ", send_date=" + send_date
				+ "]";
	}


	
	
	
}

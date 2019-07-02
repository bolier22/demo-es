package com.wanma.demoes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class TblUserAdmin implements Serializable{
	private  Integer ID;//`ID` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  	private  String Name;//`Name` varchar(50) NOT NULL,
  	private  Integer CountryCode;//`CountryCode` smallint(5) unsigned NOT NULL,
  	private  Date last_update;//`last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

}
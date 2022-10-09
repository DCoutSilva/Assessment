package com.natixis.assessment.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {
	@Id
	private String id;
	
	private String iban;
	private String accountName;
	
	public Account(){
		
	}
	public Account(String iban, String accountName){
		this.accountName = accountName;
		this.iban = iban;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public static boolean ibanValidation(String iban){
		if(iban == null || iban.equals("")) return false;
		switch (iban.substring(0, 2).toUpperCase()){
		case "NO":
			if(iban.length() == 15) return true;
			break;
		case "BE":
			if(iban.length() == 16) return true;
			break;
		case "FO":
		case "GL":
		case "DK":
		case "FI":
		case "NL":
		case "SD":
			if(iban.length() == 18) return true;
			break;
		case "MK":
		case "SI":
			if(iban.length() == 19) return true;
			break;
		case "AT":
		case "BA":
		case "EE":
		case "KZ":
		case "XK":
		case "LT":
		case "LU":
			if(iban.length() == 20) return true;
			break;
		case "HR":
		case "LV":
		case "LI":
		case "CH":
			if(iban.length() == 21) return true;
			break;
		case "BH":
		case "BG":
		case "CR":
		case "GE":
		case "DE":
		case "IE":
		case "ME":
		case "RS":
		case "GB":
		case "VA":
			if(iban.length() == 22) return true;
			break;
		case "GI":
		case "IL":
		case "TL":
		case "AE":
		case "IQ":
			if(iban.length() == 23) return true;
			break;
		case "AD":
		case "CZ":
		case "MD":
		case "PK":
		case "RO":
		case "SA":
		case "SK":
		case "ES":
		case "SE":
		case "TN":
		case "VG":
			if(iban.length() == 24) return true;
			break;
		case "PT":
		case "ST":
		case "LY":
			if(iban.length() == 25) return true;
			break;
		case "IS":
		case "TR":
			if(iban.length() == 26) return true;
			break;
		case "FR":
		case "GR":
		case "IT":
		case "MR":
		case "MC":
		case "SM":
		case "BI":
		case "DJ":
			if(iban.length() == 27) return true;
			break;
		case "AL":	
		case "AZ":
		case "CY":
		case "DO":
		case "GT":
		case "HU":
		case "LB":
		case "PL":
		case "BY":
		case "SV":
			if(iban.length() == 28) return true;
			break;
		case "BR":
		case "EG":
		case "PS":
		case "QA":
		case "UA":
			if(iban.length() == 29) return true;
			break;
		case "JO":
		case "KW":
		case "MU":
			if(iban.length() == 30) return true;
			break;
		case "MT":
		case "SC":
			if(iban.length() == 31) return true;
			break;
		case "LC":
			if(iban.length() == 32) return true;
			break;
		case "RU":
			if(iban.length() == 33) return true;
			break;
		default: return false;
		}
		return false;
		
	}
	
}

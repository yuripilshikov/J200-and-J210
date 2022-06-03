/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author YuriPilshikov
 */
@Stateless
public class ValidationBean implements ValidationBeanLocal {

    @Override
    public String checkClientType(String type) {
        if (type.isEmpty()) {
            return "Тип не указан";
        }
        if (type.length() > 100) {
            return "";
        }
        if (type.replaceAll("[a-zA-Z0-9!-_ ]", "").length() > 0) {
            return "";
        }
        return "";
    }

    @Override
    public String checkClientModel(String model) {
        if (model.isEmpty()) {
            return "Модель не указана";
        }
        if (model.length() > 100) {
            return "Размер поля МОДЕЛЬ превышает допустимое значение (100)";
        }
        if (model.replaceAll("[a-zA-Z0-9!-_ ]", "").length() > 0) {
            return "В поле ТИП допустимы только латинские символы и цифры";
        }
        return "";
    }

    @Override
    public String checkClientIp(String ip) {
        if (ip.isEmpty()) {
            return "IP адрес не указан";
        }
        if (ip.length() > 25) {
            return "Размер поля IP АДРЕС превышает допустимое значение (25)";
        }
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return "Некорректное значение IP адреса";
        }
        for (String str : parts) {
            try {
                int i = Integer.parseInt(str);
                if ((i < 0) || (i > 255)) {
                    return "Некорректное значение IP адреса";
                }
            } catch (NumberFormatException e) {
                return "Некорректное значение IP адреса";
            }
        }
        return "";
    }

    @Override
    public String checkAddressCity(String city) {
        if (city.isEmpty()) {
            return "Город не указан";
        }
        return "";
    }

    @Override
    public String checkAddressStreet(String street) {
        if (street.isEmpty()) {            
            return "Улица не указана";
        }
        return "";
    }

    @Override
    public String checkAddressNum(String numRaw) {
        if(numRaw.isEmpty()) {            
            return "Номер дома не указан";
        }        
        try {
            int num = Integer.parseInt(numRaw);
        } catch(NumberFormatException e) {            
            return "Пожалуйста, указывайте номер дома цифрами";
        }
        return "";
    }

    @Override
    public boolean checkClientFields(HttpServletRequest request, String type, String model, String ip) {
        boolean checkResult = true;
        
        String validateResult = checkClientType(type);
        if(!validateResult.isEmpty()) {            
            request.setAttribute("typeValErrMsg", validateResult);
            checkResult = false;
        }        
        
        validateResult = checkClientModel(model);
        if(!validateResult.isEmpty()) {            
            request.setAttribute("modelValErrMsg", validateResult);
            checkResult = false;
        }        
        
        validateResult = checkClientIp(ip);
        if(validateResult.length() != 0) {
            request.setAttribute("msgError", validateResult);
            request.setAttribute("ipValErrMsg", validateResult);
            checkResult = false;
        }
        return checkResult;
    }

    @Override
    public boolean checkAddressFields(HttpServletRequest request, String city, String street, String numRaw) {
        boolean checkResult = true;
        
        String validateResult = checkAddressCity(city);
        if(!validateResult.isEmpty()) {
            request.setAttribute("msgError", validateResult);
            request.setAttribute("cityValErrMsg", validateResult);
            checkResult = false;
        }       
        
        validateResult = checkAddressStreet(street);
        if(!validateResult.isEmpty()) {
            request.setAttribute("msgError", validateResult);
            request.setAttribute("streetValErrMsg", validateResult);
            checkResult = false;
        }        
        
        validateResult = checkAddressNum(numRaw);
        if(!validateResult.isEmpty()) {
            request.setAttribute("msgError", validateResult);
            request.setAttribute("numValErrMsg", validateResult);
            checkResult = false;
        }
        return checkResult;
    }
    
        
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.ejb.Stateless;
import model.Client;

/**
 *
 * @author YuriPilshikov
 */
@Stateless
public class RowBuilderBean implements RowBuilderBeanLocal {

    @Override
    public String createRow(Client c) {
        String s = null;
        int as = c.getAddresses().size();

        if (as > 1) {
            StringBuilder sb = new StringBuilder();
            s = "<tr>\n" + "<td rowspan=\" " + as + "\">"
                    + c.getIdClient() + "</td>\n"
                    + "<td rowspan=\"" + as + "\">" + c.getType() + "</td>\n"
                    + "<td rowspan=\"" + as + "\">" + c.getModel() + "</td>\n"
                    + "<td rowspan=\"" + as + "\">" + c.getIp() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getIdAddress() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getCity() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getStreet() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getNum() + "</td>\n"
                    + "<td>" + (c.getAddresses().get(0).getSubnum() < 0 ? "-" : c.getAddresses().get(0).getSubnum()) + "</td>\n"
                    + "<td>" + (c.getAddresses().get(0).getFlat() < 0 ? "-" : c.getAddresses().get(0).getFlat()) + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getExtra() + "</td>\n"
                    + "<td><a href=\"http://localhost:26213/J200_HW_part2/update2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Редактировать" + "</a>"
                    + "<a href=\"http://localhost:26213/J200_HW_part2/delete2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Удалить" + "</a>"
                    + "</td>\n"
                    + "</tr>\n";
            sb.append(s);
            for (int i = 1; i < as; i++) {
                s = "<tr>"
                    + "<td>" + c.getAddresses().get(i).getIdAddress() + "</td>\n"
                    + "<td>" + c.getAddresses().get(i).getCity() + "</td>\n"
                    + "<td>" + c.getAddresses().get(i).getStreet() + "</td>\n"
                    + "<td>" + c.getAddresses().get(i).getNum() + "</td>\n"
                    + "<td>" + (c.getAddresses().get(i).getSubnum() < 0 ? "-" : c.getAddresses().get(i).getSubnum()) + "</td>\n"
                    + "<td>" + (c.getAddresses().get(i).getFlat() < 0 ? "-" : c.getAddresses().get(i).getFlat()) + "</td>\n"
                    + "<td>" + c.getAddresses().get(i).getExtra() + "</td>\n"
                    + "<td><a href=\"http://localhost:26213/J200_HW_part2/update2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(i).getIdAddress() + "\">" + "Редактировать" + "</a>"
                    + "<a href=\"http://localhost:26213/J200_HW_part2/delete2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(i).getIdAddress() + "\">" + "Удалить" + "</a>"
                    + "</td>\n"
                    + "</tr>\n";
                sb.append(s);
            }
            s = sb.toString();
        } else {
            s = "<tr>\n" + "<td>"
                    + c.getIdClient() + "</td>\n"
                    + "<td>" + c.getType() + "</td>\n"
                    + "<td>" + c.getModel() + "</td>\n"
                    + "<td>" + c.getIp() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getIdAddress() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getCity() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getStreet() + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getNum() + "</td>\n"
                    + "<td>" + (c.getAddresses().get(0).getSubnum() < 0 ? "" : c.getAddresses().get(0).getSubnum()) + "</td>\n"
                    + "<td>" + (c.getAddresses().get(0).getFlat() < 0 ? "" : c.getAddresses().get(0).getFlat()) + "</td>\n"
                    + "<td>" + c.getAddresses().get(0).getExtra() + "</td>\n"
                    + "<td><a href=\"http://localhost:26213/J200_HW_part2/update2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Редактировать" + "</a>"
                    + "<a href=\"http://localhost:26213/J200_HW_part2/delete2?cid=" + c.getIdClient() + "&aid=" + c.getAddresses().get(0).getIdAddress() + "\">" + "Удалить" + "</a>"
                    + "</td>\n"
                    + "</tr>\n";
        }
        return s;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

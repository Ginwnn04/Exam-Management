/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author pc
 */
public class Format {
   public static DecimalFormat formatNumber = new DecimalFormat("###,###,###", new DecimalFormatSymbols(Locale.US));
   public static SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:s");
}

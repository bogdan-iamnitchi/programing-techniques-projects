package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {

    private Map<Integer, Float> termeni;

    public Polynomial(Map<Integer, Float> sursa){
        this.setTermeni(sursa);
    }
    public Polynomial(String sursa) throws Exception {
        termeni = new HashMap<>();
        parse(sursa);
    }

    public void adauga(int exp, float coef) {
        float coef2;
        if(termeni.containsKey(exp)) {
            coef2 = Math.round((coef + termeni.get(exp))*1000.0f)/1000.0f;
        }
        else {
            coef2 = Math.round(coef*1000.0f)/1000.0f;
        }
        if (coef2 == 0.0f)
            this.elimina(exp);
        else
            this.termeni.put(exp, coef2);
        sort(termeni);
    }

    public void adauga(Polynomial p) {
        float coef;
        for(Map.Entry<Integer, Float> termen : p.getTermeni().entrySet()){
            if(termeni.containsKey(termen.getKey())) {
                coef = Math.round((termen.getValue() + termeni.get(termen.getKey()))*1000.0f)/1000.0f;
            }
            else {
                coef = Math.round(termen.getValue()*1000.0f)/1000.0f;
            }
            if (coef == 0.0f)
                this.elimina(termen.getKey());
            else
                this.termeni.put(termen.getKey(), coef);
        }
        sort(termeni);
    }

    public void elimina(int key) {
        this.termeni.remove(key);
    }

    public void setTermeni(Map<Integer, Float> termeni) { this.termeni = termeni; }
    public Map<Integer, Float> getTermeni() { return termeni; }
    public float getTermen(int exp){
        return this.termeni.get(exp);
    }

    public void inverseaza() { termeni.replaceAll((key, value) -> -value); }
    public void reseteaza() { termeni.replaceAll((key, value) -> 0.0f); }

    public int grad() {
        int max = -1;
        for(Map.Entry<Integer, Float> termen : termeni.entrySet()) {
            max = (max < termen.getKey() ? termen.getKey() : max);
        }
        return max;
    }

    public void sort(Map<Integer, Float> valori) {
        TreeMap<Integer, Float> sortedMap = new TreeMap<>(Collections.reverseOrder());
        sortedMap.putAll(valori);
        setTermeni(sortedMap);

    }

    public float evalueaza(float value) {
        float rez = 0.0f;
        for(Map.Entry<Integer, Float> termen : termeni.entrySet()) {
            rez += termen.getValue()*Math.pow(value, termen.getKey());
        }
        return rez;
    }

    //-------------------------------------------------------------------------------------------------------PARSARE
    public void parse(String sursa) throws Exception {
        try {
            sursa = sursa.replace(" ", "");
            Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
            Matcher matcher = pattern.matcher(sursa);
            while (matcher.find()) {
                String s = matcher.group(1);
                if (s.length() != 0) {
                    if (!s.startsWith("-"))
                        checkPoz(s);
                    else
                        checkNeg(s);
                }
            }
        }
        catch (Exception e) {
            throw new Exception("Oops, ai introdus un polinom invalid");
        }
    }

    public void checkPoz(String s) {
        if(!s.contains("x")) { //constanta
            this.adauga(0, Float.parseFloat(s));
        }
        else if(s.equals("x")) { //primu element
            this.adauga(1, (float)1.0);
        }
        else if(s.equals("+x")) { //1*x^1
            this.adauga(1, (float)1.0);
        }
        else if(s.endsWith("x"))  {  //cnt*x^1
            this.adauga(1, Float.parseFloat(s.substring(0, s.length()-1)));
        }
        else if(s.startsWith("x^")) { //1*x^ceva
            this.adauga(Integer.parseInt(s.substring(2)), (float)1.0);
        }
        else if(s.startsWith("+x^")) { //1*x^ceva
            this.adauga(Integer.parseInt(s.substring(3)), (float)1.0);
        }
        else { //cnt*x^ceva
            String s1 = s.substring(0, s.indexOf("x"));
            String s2 = s.substring(s.indexOf("x")+2);
            this.adauga(Integer.parseInt(s2), Float.parseFloat(s1));
        }
    }

    public void checkNeg(String s) {
        if(!s.contains("x")) { //constanta
            this.adauga(0, Float.parseFloat(s));
        }
        else if(s.equals("-x")) { //1*x^1
            this.adauga(1, (float)-1.0);
        }
        else if(s.endsWith("x"))  {  //cnt*x^1
            this.adauga(1, Float.parseFloat(s.substring(0, s.length()-1)));
        }
        else if(s.startsWith("-x^")) { //1*x^ceva
            this.adauga(Integer.parseInt(s.substring(3)), (float)-1.0);
        }
        else { //cnt*x^ceva
            String s1 = s.substring(0, s.indexOf("x"));
            String s2 = s.substring(s.indexOf("x")+2);
            this.adauga(Integer.parseInt(s2), Float.parseFloat(s1));
        }
    }

    //-------------------------------------------------------------------------------------------------------AFISARE
    public String printMonom(int exp, float coef){
        if(coef == 0) // caz 0x
            return "";
        else if(exp == 0) //caz x^0
            return coef + "";
        else if (coef == 1 && exp == 1) //caz x^1
            return "x";
        else if(coef == -1 && exp == 1) //caz -x^1
            return "-x";
        else if(coef != 1 && coef != -1 && exp == 1) // caz 2x,3x sau -2x, -3x
            return coef + "x";
        else if(coef == 1 && exp != 1) //caz x^2
            return "x^" + exp;
        else if(coef == -1)  //caz -x^2
            return "-x^" + exp;
        else  // caz 3x^4
            return coef + "x^" + exp;
    }

    public String toString(){
        StringBuilder polinom = new StringBuilder();
        int cnt = 0;
        for(Map.Entry<Integer, Float> termen : termeni.entrySet()) {
            if(printMonom(termen.getKey(), termen.getValue()).startsWith("-")) {
                polinom.append(printMonom(termen.getKey(), termen.getValue()));
                cnt++;
            }
            else if(termen.getValue()!=0)
                    polinom.append(cnt++ != 0 ? "+" + printMonom(termen.getKey(), termen.getValue()) : printMonom(termen.getKey(), termen.getValue()));
        }
        return polinom.toString();
    }

    public boolean equals(Polynomial o) {
        for(Map.Entry<Integer, Float> termen : termeni.entrySet()) {
            if(o.getTermeni().containsKey(termen.getKey())) {
                if(o.getTermen(termen.getKey()) != termen.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }
}

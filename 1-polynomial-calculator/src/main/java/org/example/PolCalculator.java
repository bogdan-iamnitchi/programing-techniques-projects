package org.example;

import java.util.Map;

public class PolCalculator {

    private Polynomial rezultat;
    private Polynomial Q ;
    private Polynomial R;

    public PolCalculator(){ }

    public Polynomial getQ() {
        return Q;
    }

    public Polynomial getR() {
        return R;
    }

    public Polynomial aduna(Polynomial p1, Polynomial p2) throws Exception{
        this.rezultat = new Polynomial("");
        rezultat.adauga(p1);
        rezultat.adauga(p2);
        return rezultat;
    }

    public Polynomial scade(Polynomial p1, Polynomial p2) throws Exception{
        this.rezultat = new Polynomial("");
        p2.inverseaza();
        return this.aduna(p1, p2);
    }

    public Polynomial inmulteste(Polynomial p1, Polynomial p2) throws Exception{
        this.rezultat = new Polynomial("");
        for(Map.Entry<Integer, Float> termen1 : p1.getTermeni().entrySet()){
            for(Map.Entry<Integer, Float> termen2 : p2.getTermeni().entrySet()){
                rezultat.adauga(termen1.getKey()+termen2.getKey(), termen1.getValue() * termen2.getValue());
            }
        }
        return rezultat;
    }

    public void imparte(Polynomial p1, Polynomial p2) throws Exception{
        this.Q = new Polynomial("");
        this.R = new Polynomial("");
        Polynomial div = new Polynomial("");
        Polynomial aux= new Polynomial(p1.getTermeni());

        while(aux.grad() >= p2.grad()) {
            div.adauga(aux.grad() - p2.grad(), aux.getTermen(aux.grad()) / p2.getTermen(p2.grad()));
            Q.adauga(div);

            aux = scade(aux, inmulteste(p2, div));
            div.reseteaza();
        }
        R.adauga(aux);
    }

    public Polynomial deriveaza(Polynomial p) throws Exception{
        this.rezultat = new Polynomial("");
        for(Map.Entry<Integer, Float> termen1 : p.getTermeni().entrySet()){
                rezultat.adauga(termen1.getKey()-1, termen1.getValue() * termen1.getKey());
        }
        return rezultat;
    }

    public Polynomial intergreaza(Polynomial p) throws Exception{
        this.rezultat = new Polynomial("");
        for(Map.Entry<Integer, Float> termen1 : p.getTermeni().entrySet()){
            int newExp = termen1.getKey()+1;
            float newCoef = termen1.getValue() / newExp;
            rezultat.adauga(newExp, newCoef);
        }
        return rezultat;
    }
}

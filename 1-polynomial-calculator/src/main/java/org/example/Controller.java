package org.example;

public class Controller {

    private final PolCalculator calculator;
    private final CalcView screen;

    private Polynomial p1;
    private Polynomial p2;
    public Controller(PolCalculator calculator, CalcView screen){
        this.calculator = calculator;
        this.screen = screen;

        solveButtons();
    }

    public void solveButtons() {

        screen.addResetListener(e -> {
            try {
                screen.resetAll();
            }
            catch (Exception exp) {
                screen.showError(exp.getMessage());
            }
        });


        screen.addComputeListener(e -> {

            try {
                p1 = new Polynomial(screen.getPol1Text());
                p2 = new Polynomial(screen.getPol2Text());

                String operationBasic = screen.getBasicOperation();
                String operationAdvanced = screen.getAdvancedOperation();

                screen.removeListResult();
                if (screen.getCheckBasic()) {
                    switch (operationBasic) {
                        case "Add" -> {
                            screen.addListResult("Add: ", calculator.aduna(p1, p2));
                            screen.addListHistory(new HistoryEntry("Add: ", p1, p2));
                        }
                        case "Subtract" -> {
                            screen.addListResult("Subtract: ", calculator.scade(p1, p2));
                            screen.addListHistory(new HistoryEntry("Subtract: ", p1, p2));
                        }
                        case "Multiply" -> {
                            screen.addListResult("Multiply: ", calculator.inmulteste(p1, p2));
                            screen.addListHistory(new HistoryEntry("Multiply: ", p1, p2));
                        }

                        case "Devide" -> {
                            calculator.imparte(p1, p2);
                            screen.addListHistory(new HistoryEntry("Devide: ", p1, p2));
                            screen.addListResult("Q: ", calculator.getQ());
                            screen.addListResult("R: ", calculator.getR());
                        }
                    }
                }
                if (screen.getCheckAdvanced()) {
                    switch (operationAdvanced) {
                        case "Derivative of first" -> {
                            screen.addListResult("Derivative 1st: ", calculator.deriveaza(p1));
                            screen.addListHistory(new HistoryEntry("Derivative 1st: ", p1, new Polynomial("")));
                        }

                        case "Derivative of second" -> {
                            screen.addListResult("Derivative 2nd: ", calculator.deriveaza(p2));
                            screen.addListHistory(new HistoryEntry("Derivative 2nd: ", new Polynomial(""), p2));
                        }
                        case "Integrate first" -> {
                            screen.addListResult("Integral 1st: ", calculator.intergreaza(p1));
                            screen.addListHistory(new HistoryEntry("Integral 1st: ", p1, new Polynomial("")));
                        }
                        case "Integrate second" -> {
                            screen.addListResult("Integral 2nd: ", calculator.intergreaza(p2));
                            screen.addListHistory(new HistoryEntry("Integral 2nd: ", new Polynomial(""), p2));
                        }
                    }
                }
                screen.refreshHistoryList();
            }
            catch (Exception exp) {
                screen.showError(exp.getMessage());
            }

        });
    }
}

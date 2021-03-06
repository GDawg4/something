import java.util.*;

public class main{
    static HashMap<String, Functions> allFunctions = new HashMap<>();

    private static String[] tokenize(String code) {
        return code.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim().split("\\s+");
    }

    static List atom(String token) {
        List dummyList = new ArrayList<>();
        try {
            Integer.parseInt(token);
            dummyList.add(token);
            return dummyList;
        } catch (NumberFormatException e) {
            try {
                Float.parseFloat(token);
                dummyList.add(token);
                return dummyList;
            } catch (NumberFormatException e2) {
                try {
                    Double.parseDouble(token);
                    dummyList.add(token);
                    return dummyList;
                } catch (NumberFormatException e3) {
                    dummyList.add(token);
                    return dummyList;
                }
            }
        }
    }

    private static List trim(List token){
        for (int i = 0; i< token.size(); i++){
            if (token.get(i) instanceof List){
                if (((List) token.get(i)).size() == 1){
                    Object objectToSimplify = ((List) token.get(i)).get(0);
                    token.remove(i);
                    token.add(i, objectToSimplify);
                }
                else{
                    trim(((List) token.get(i)));
                }
            }
        }
        return token;
    }

    private static List read(List<String> tokens) throws Exception {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("unexpected EOF while reading");
        }
        String token = tokens.remove(0);

        if (token.equals("(")) {
            List<Object> atoms = new ArrayList<>(tokens.size() - 1);
            while (!tokens.get(0).equals(")"))
                atoms.add(read(tokens));
            tokens.remove(0);
            return atoms;
        } else if (token.equals(")")) {
            throw new Exception("unexpected ')'");
        } else {
            return atom(token);
        }
    }

    private static List parse(String code) throws Exception {
        return read(new ArrayList<String>(Arrays.asList(tokenize(code))));
    }

    public static boolean isSimpleEnough(Object something){
        return something instanceof List;
    }

    private static List simplify(List toBeSimplified){
        System.out.println(toBeSimplified.get(0) + " Primera cosa");

        if (toBeSimplified.size() == 1){
            System.out.println("just this");
        }

        if (!toBeSimplified.get(0).toString().equals("cond")){
            for (int i = 0; i<toBeSimplified.size(); i++){
                if (toBeSimplified.get(i) instanceof List){
                    Object objectToSimplified = toBeSimplified.get(i);
                    System.out.println(objectToSimplified.toString() + "Must be made simpler");

                    if (isSimpleEnough(toBeSimplified)){
                        simplify(((List) toBeSimplified.get(i)));
                    }
                    System.out.println("Procesando");
                    System.out.println(toBeSimplified.get(i) + " En esto estamos");
                    System.out.println(toBeSimplified + " Antes");

                    if (toBeSimplified.get(i) instanceof  List && ((List) toBeSimplified.get(i)).size() == 1){
                        Collections.replaceAll(toBeSimplified, toBeSimplified.get(i), ((List) toBeSimplified.get(i)).get(0));
                    }else {
                        Collections.replaceAll(toBeSimplified, toBeSimplified.get(i), processArithmetic(((List) toBeSimplified.get(i))));
                    }
                    System.out.println(toBeSimplified + " Despues");
                }
            }
        }
        else{
            System.out.println("Cond here");
            System.out.println(toBeSimplified + " Esto se hará simple");
            System.out.println(processConditionals(toBeSimplified) + " saliendo del cond");
            Collections.replaceAll(toBeSimplified, toBeSimplified.get(1), processConditionals((toBeSimplified)));
            toBeSimplified.remove(0);
            while (toBeSimplified.size() > 1){
                toBeSimplified.remove(toBeSimplified.size()-1);
            }
            System.out.println(toBeSimplified + "Esto queda");
        }
        return toBeSimplified;
    }

    public static Number processArithmetic(List instructions){
        String toCheck = instructions.get(0).toString();
        instructions.remove(0);
        Double result = 0.0;
        switch (toCheck){
            case "+":
                for (Object i: instructions){
                    result += Double.parseDouble(i.toString());
                }
                break;
            case "-":
                for (Object i: instructions){
                    result -= Double.parseDouble(i.toString());
                }
                result += 2*Double.parseDouble(instructions.get(0).toString());
                break;
            case "*":
                result = 1.0;
                for (Object i: instructions){
                    result *= Double.parseDouble(i.toString());
                }
                break;
            case "/":
                Double firstDigit = Double.parseDouble(instructions.get(0).toString());
                Double secondDigit = Double.parseDouble((instructions.get(1).toString()));
                result = firstDigit/secondDigit;
                break;

        }
        return result;
    }

    private static Number processConditionals(List instructionsCond){
        System.out.println(instructionsCond + " Instrucciones cond");

        for (int i = 1; i < instructionsCond.size(); i++){
            String commandToExecute; //definitely not number 66
            Object testToCast = ((List)((List) instructionsCond.get(i)).get(0)).get(0);
            System.out.println(testToCast + " por acá");

            if (((List) instructionsCond.get(i)).get(0).toString().equals("F") || ((List) instructionsCond.get(i)).get(0).toString().equals("T")){
                commandToExecute = ((List) instructionsCond.get(i)).get(0).toString();
            }else {
                commandToExecute = testToCast.toString();
            }
            String firstComparable =((List)((List) instructionsCond.get(i)).get(0)).get(1).toString();
            String secondComparable = ((List)((List) instructionsCond.get(i)).get(0)).get(2).toString();

            switch (commandToExecute){

                case "=":
                    if (firstComparable.compareTo(secondComparable) == 0){
                        return Integer.parseInt(((List) instructionsCond.get(i)).get(1).toString());
                    }
                    break;

                case ">":
                    if (firstComparable.compareTo(secondComparable) > 1){
                        return Integer.parseInt(((List) instructionsCond.get(i)).get(1).toString());
                    }
                    break;

                case "<":
                    if (firstComparable.compareTo(secondComparable) < 1){
                        return Integer.parseInt(((List) instructionsCond.get(i)).get(1).toString());
                    }
                    break;
                case "T":
                    return Integer.parseInt(((List) instructionsCond.get(i)).get(1).toString());
            }
        }
        return 0;
    }

    public static void main(String[] args) {

        do {
            String code;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese algo");
            code = scanner.nextLine();
            try {
                System.out.println(trim(parse(code)));
                if (trim(parse(code)).get(0).equals("defun")){
                    Functions newFunction = new Functions(trim(parse(code)).get(1).toString(),
                            (List) trim(parse(code)).get(3),
                            (List) trim(parse(code)).get(2));
                    allFunctions.put(newFunction.getNombre(), newFunction);

                } else if (allFunctions.containsKey(trim(parse(code)).get(0).toString())){
                    System.out.println("Found it");
                    ArrayList<Object> values = new ArrayList();
                    Functions functionToDo = allFunctions.get(trim(parse(code)).get(0).toString());

                    for (Object i: (List)trim(parse(code)).get(1)){
                        values.add(i);
                    }
                    System.out.println(processArithmetic(simplify(functionToDo.replace(values, functionToDo.getFuncion()))));
                }
                else{
                    System.out.println(processArithmetic(simplify(trim(parse(code)))));
                }
            }catch (Exception e){
                System.out.println("Something went really wrong");
                e.printStackTrace();
            }
        }while (true);
    }
}


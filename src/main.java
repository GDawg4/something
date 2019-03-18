import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class main{
    private static String[] tokenize(String code) {
        return code.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim().split("\\s+");
    }

    private static List atom(String token) {
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

    public static boolean checkIfSimpleEnough(Object something){
        return something instanceof List;
    }

    private static List simplify(List toBeSimplified){
        for (int i = 1; i<toBeSimplified.size(); i++){
            if (toBeSimplified.get(i) instanceof List){
                Object objectToSimplified = toBeSimplified.get(i);
                System.out.println(objectToSimplified.toString() + "Must be made simpler");

                if (checkIfSimpleEnough(toBeSimplified)){
                    simplify(((List) toBeSimplified.get(i)));

                }
                System.out.println("Se llegó");
                Collections.replaceAll(toBeSimplified, toBeSimplified.get(i), process(((List) toBeSimplified.get(i))));
            }
        }
        System.out.println(toBeSimplified + "new stuff");
        return toBeSimplified;
    }

    private static Number process(List instructions){
        System.out.println("Se intentó");
        String toCheck = instructions.get(0).toString();
        instructions.remove(0);
        Integer result = 0;
        switch (toCheck){
            case "+":
                for (Object i: instructions){
                    result += Integer.parseInt(i.toString());
                }
                break;
            case "-":
                for (Object i: instructions){
                    result -= Integer.parseInt(i.toString());
                }
                break;
            case "*":
                result = 1;
                for (Object i: instructions){
                    result *= Integer.parseInt(i.toString());
                    System.out.println(result);
                }
                break;
            case "/":
                result = 1;
                for (Object i: instructions){
                    result /= Integer.parseInt(i.toString());
                }
                break;
        }
        return result;
    }

    public static void main(String[] args) {
        do {
            String code;
            Scanner scanner = new Scanner(System.in);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ingrese algo");
            code = scanner.nextLine();
            try {
                System.out.println(trim(parse(code)));
                System.out.println(simplify(trim(parse(code))));
                System.out.println(process(simplify(trim(parse(code)))));
            }catch (Exception e){
                System.out.println("Something went really wrong");
                e.printStackTrace();
            }
        }while (true);
    }
}


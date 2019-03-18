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
        System.out.println(token);
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

    private static void process(List instructions){
        for (int i = 0; i < instructions.size(); i++){
            if (instructions.get(i) instanceof List){
                process(((List) instructions.get(i)));
            }else {
                System.out.println("Easy process");
            }
        }
    }

    public static void main(String[] args) {
        do {
            String code;
            Scanner scanner = new Scanner(System.in);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ingrese algo");
            code = scanner.nextLine();
            System.out.println(Arrays.toString(tokenize(code)));
            try {
                System.out.println(parse(code));
                trim(parse(code));
                process(trim(parse(code)));
            }catch (Exception e){
                System.out.println("Something went really wrong");
            }
        }while (true);
    }
}


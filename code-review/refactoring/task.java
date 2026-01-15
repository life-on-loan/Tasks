// TODO: сделать ревью, найти проблемы
class CodeProcessor {

    public void process(List<Code> codes) {
        for (Code code : codes) {
            if (CodeType.ITCP == code.getCodeType()) {
                doSmthngITCP();
            }
            else if (CodeType.TLS == code.getCodeType()) {
                doSmthngTLS();
            }
            else if (CodeType.OTHER == code.getCodeType()) {
                doSmthngOther();
            }
            else {
                doDefault();
            }
        }
    }

    private void doSmthngITCP() {
        System.out.println("Handling ITCP");
    }

    private void doSmthngTLS() {
        System.out.println("Handling TLS");
    }

    private void doSmthngOther() {
        System.out.println("Handling Other");
    }

    private void doDefault() {
        System.out.println("Handling Default Case");
    }
}

enum CodeType {
    ITCP, TLS, OTHER
}

class Code {
    private final CodeType codeType;

    public Code(CodeType codeType) {
        this.codeType = codeType;
    }

    public CodeType getCodeType() {
        return codeType;
    }
}

public class CodeProcessingApp {
    public static void main(String[] args) {
        List<Code> codes = Arrays.asList(
                new Code(CodeType.ITCP),
                new Code(CodeType.TLS),
                new Code(CodeType.OTHER)
        );

        CodeProcessor processor = new CodeProcessor();
        processor.process(codes);
    }
}

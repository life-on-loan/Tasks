import java.util.*;

class CodeProcessor {
    private final Map<CodeType, Runnable> handlers = new EnumMap<>(CodeType.class);

    public CodeProcessor() {
        handlers.put(CodeType.ITCP, this::doSmthngITCP);
        handlers.put(CodeType.TLS, this::doSmthngTLS);
        handlers.put(CodeType.OTHER, this::doSmthngOther);
    }

    public void process(List<Code> codes) {
        for (Code code : codes) {
            handlers.getOrDefault(code.getCodeType(), this::doDefault).run();
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
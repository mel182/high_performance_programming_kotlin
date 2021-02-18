package optimizing_access_to_properties;

public class JavaTestClass {

    public static void main(String[] args) {

        System.out.println("Optimizing constant: "+OptimizeConstants.Foo); //  Calling the const val in 'EfficientConstantDeclaration.kt' from Java file
        System.out.println("Optimizing constant: "+OptimizeConstants.Foo2); //  Calling the const val in 'EfficientConstantDeclaration.kt' from Java file
    }

}

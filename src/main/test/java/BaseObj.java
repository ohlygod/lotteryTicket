import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class BaseObj<T,U> {
    private List<T> items;
    private List<String> names;
    private BaseObj baseObj1;
    private BaseObj<T, U> baseObj2;
    private List list;
    private Map<T, U> map = new HashMap<>();
    private T t;


    private <E> T getItem(T t, E e) {
        return t;
    }

    public BaseObj<T, U> test(List<T> items, BaseObj<Integer, Integer> nums, T t) {
        return null;
    }

    public static void main(String[] args) {

        System.out.println("---------------输出泛型类尖括号里的类型----------------------------------------");
        // 用当前类名获取class对象无需getClass方法，只要类的实例才需要getClass方法
        Class<BaseObj> baseObjClass = BaseObj.class;
        // getTypeParameters方法获取泛型类/接口尖括号里面的泛型变量：比如类名<var1,var2...>,getTypeParameters就是获取var1,var2...
        TypeVariable<Class<BaseObj>>[] typeParameters = baseObjClass.getTypeParameters();
        for (TypeVariable<Class<BaseObj>> typeParameter : typeParameters) {
            System.out.println(typeParameter);
        }
        System.out.println("----------------输出泛型类属性中的泛型类型------------------------------------------------------");

        Field[] declaredFields = BaseObj.class.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field.getType() + "=" + field.getName());
            Type genericType = field.getGenericType(); // 获取完整泛型：类名<泛型>

            if (genericType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) genericType;
                System.out.println("Typename :" + pType.getTypeName());  // 获取完整泛型：全路径类名<泛型>
                System.out.println("rawType :" + pType.getRawType()); // 获取泛型尖括号前面的类型
                System.out.println("ownerType :" + pType.getOwnerType()); // 获取父类类型

                // 获取泛型尖括号<>里面的实际数据类型，因为可能存在多个泛型，比如 SuperClass<T, V>，所以会返回 Type[] 数组
                Type[] types = pType.getActualTypeArguments();
                System.out.println("泛型参数列表： " + Arrays.asList(types));

                System.out.println("------------------------------------------");
                continue;
            }
            System.out.println("成员变量：" + field.getName() + "不是泛型变量！");
            System.out.println("genericType =>" + genericType);
            System.out.println("------------------------------------------");
        }

    }
}
 
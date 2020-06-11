import util.Print;

/**
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/11 11:40
 */
public class PrintTest {
    public static void main(String[] args) {
        Print.PN("黑色", Print.BLACK);
        Print.PN("白色", Print.WHITE);
        Print.PN("红色", Print.RED);
        Print.PN("绿色", Print.GREEN);
        Print.PN("黄色", Print.YELLOW);
        Print.PN("蓝色", Print.BLUE);
        Print.PN("品红", Print.MAGENTA);
        Print.PN("蓝绿", Print.CYAN);
        Print.PN("黑底白字", Print.WHITE, Print.BLACK_BACKGROUND);
        Print.PN("白底黑字", Print.BLACK, Print.WHITE_BACKGROUND);
        Print.PN("蓝底红字", Print.RED, Print.BLUE_BACKGROUND);
        Print.PN("加粗倾斜", Print.BOLD, Print.ITATIC);
        Print.PN("黄底白字下划线", Print.WHITE, Print.YELLOW_BACKGROUND, Print.UNDERLINE);
        Print.PN("红字颜色反转", Print.RED, Print.REVERSE);
    }
}

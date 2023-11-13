package christmas.domain.benefit.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.menu.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountedMenusTest {
    @Test
    @DisplayName("메뉴 할인가격 저장 테스트")
    void saveDiscountPrice() {
        DiscountedMenus discountedMenus = new DiscountedMenus();
        discountedMenus.discountEachMenu(Menu.CHRISTMAS_PASTA, 3000, 2);

        assertThat(discountedMenus.getDiscountedPrice(Menu.CHRISTMAS_PASTA)).isEqualTo(6000);
    }

    @Test
    @DisplayName("메뉴 할인가격 합계 테스트")
    void discountPriceSum() {
        DiscountedMenus discountedMenus = new DiscountedMenus();
        discountedMenus.discountEachMenu(Menu.CHRISTMAS_PASTA, 3000, 1);
        discountedMenus.discountEachMenu(Menu.CHAMPAGNE, 5000, 1);
        discountedMenus.discountEachMenu(Menu.BARBECUE_RIBS, 2000, 1);

        assertThat(discountedMenus.getDiscountedTotalPrice()).isEqualTo(10000);
    }

    @Test
    @DisplayName("DiscountedMenus 끼리 잘 합쳐지는지 확인")
    void addDiscountedMenus() {
        DiscountedMenus addDiscountedMenus = new DiscountedMenus();
        addDiscountedMenus.discountEachMenu(Menu.CHAMPAGNE, 3000, 1);
        addDiscountedMenus.discountEachMenu(Menu.BARBECUE_RIBS, 5000, 2);
        addDiscountedMenus.discountTotalPrice(1000);

        DiscountedMenus discountedMenus = new DiscountedMenus();
        discountedMenus.discountEachMenu(Menu.CHOCOLATE_CAKE, 2000, 1);
        discountedMenus.discountEachMenu(Menu.MUSHROOM_SOUP, 1000, 1);
        discountedMenus.discountTotalPrice(2000);

        discountedMenus.add(addDiscountedMenus);

        assertThat(discountedMenus.getDiscountedPrice(Menu.CHAMPAGNE)).isEqualTo(3000);
        assertThat(discountedMenus.getDiscountedPrice(Menu.BARBECUE_RIBS)).isEqualTo(10000);
        assertThat(discountedMenus.getDiscountedPrice(Menu.CHOCOLATE_CAKE)).isEqualTo(2000);
        assertThat(discountedMenus.getDiscountedPrice(Menu.MUSHROOM_SOUP)).isEqualTo(1000);
        assertThat(discountedMenus.getDiscountedTotalPrice()).isEqualTo(19000);
    }
}
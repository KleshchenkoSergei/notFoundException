package ru.netology.domain;

import org.junit.jupiter.api.Test;
import ru.netology.manager.ProductManager;
import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Smartphone item1 = new Smartphone(1, "Linux. Смартфон PinePhone", 15000, "PINE64");
    Smartphone item2 = new Smartphone(2, "Смартфон PinePhone PRO", 20000, "PINE64");
    Smartphone item3 = new Smartphone(3, "Смартфон Fairphone", 57000, "Fairphone");
    Book item4 = new Book(4, "Книга Just for fun", 1500, "Линус Торвальдс");
    Book item5 = new Book(5, "Книга UNIX изнутри", 1200, "Ю. Вахалия");
    Book item6 = new Book(6, "Linux. Книга Операционная система UNIX", 1000, "Андрей Робачевский");

    ProductRepository repo = new ProductRepository();
    ProductManager manager = new ProductManager(repo);
    Smartphone phone = new Smartphone();
    Book book = new Book();

    @Test
    public void shouldSaveEmpty() {

        repo.save(item4);

        Product[] expected = new Product[]{item4};
        Product[] actual = repo.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSave() {

        repo.save(item1);
        repo.save(item4);

        Product[] expected = new Product[]{item1, item4};
        Product[] actual = repo.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRemoveById() {

        repo.save(item1);
        repo.save(item2);
        repo.save(item3);
        repo.save(item4);
        repo.removeById(3);

        Product[] expected = new Product[]{item1, item2, item4};
        Product[] actual = repo.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRemoveByIdUnderLimit() {

        repo.save(item1);
        repo.save(item2);
        repo.save(item3);
        repo.save(item4);

        //check exception
        assertThrows(NotFoundException.class, () -> {
            repo.removeById(100);
        });
    }

    @Test
    public void shouldRemoveByIdEmpty() {

        //check exception
        assertThrows(NotFoundException.class, () -> {
            repo.removeById(100);
        });
    }

    @Test
    public void shouldAddEmpty() {

        manager.add(item1);
        manager.add(item4);

        Product[] expected = new Product[]{item1, item4};
        Product[] actual = repo.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldAdd() {

        manager.add(item1);
        manager.add(item4);

        Product[] expected = new Product[]{item1, item4};
        Product[] actual = repo.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByFromOneElement() {

        repo.save(item2);

        Product[] expected = {item2};
        Product[] actual = manager.searchBy("Смартфон PinePhone PRO");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByFromSeveralElements() {

        repo.save(item1);
        repo.save(item2);
        repo.save(item3);
        repo.save(item6);

        Product[] expected = {item6};
        Product[] actual = manager.searchBy("Linux. Книга Операционная система UNIX");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByMultiSearch() {

        repo.save(item1);
        repo.save(item2);
        repo.save(item3);
        repo.save(item6);

        Product[] expected = {item1, item6};
        Product[] actual = manager.searchBy("Linux");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByNoMatches() {

        repo.save(item1);
        repo.save(item2);
        repo.save(item5);
        repo.save(item6);

        Product[] expected = {};
        Product[] actual = manager.searchBy("Смартфон Fairphone");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByEmptyArray() {

        Product[] expected = {};
        Product[] actual = manager.searchBy("");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByAuthorSeveralElements() {

        repo.save(item1);
        repo.save(item2);
        repo.save(item4);
        repo.save(item6);

        Product[] expected = {item4};
        Product[] actual = manager.searchBy("Линус Торвальдс");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByAuthorOneElement() {

        repo.save(item4);

        Product[] expected = {item4};
        Product[] actual = manager.searchBy("Линус Торвальдс");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByAuthorEmpty() {

        Product[] expected = {};
        Product[] actual = manager.searchBy("Линус Торвальдс");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByAuthorNoResults() {

        repo.save(item1);
        repo.save(item2);
        repo.save(item4);
        repo.save(item6);

        Product[] expected = {};
        Product[] actual = manager.searchBy("Линус Торвальдс!!!");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByVendorSeveralElements() {

        repo.save(item1);
        repo.save(item2);
        repo.save(item4);
        repo.save(item6);

        Product[] expected = {item1, item2};
        Product[] actual = manager.searchBy("PINE64");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByVendorOneElement() {

        repo.save(item1);

        Product[] expected = {item1};
        Product[] actual = manager.searchBy("PINE64");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByVendorEmpty() {

        Product[] expected = {};
        Product[] actual = manager.searchBy("PINE64");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByVendorNoResults() {

        repo.save(item1);
        repo.save(item2);
        repo.save(item4);
        repo.save(item6);

        Product[] expected = {};
        Product[] actual = manager.searchBy("PINE641");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldMatchesBookTrue() {

        boolean expected = true;
        Product product = item4;
        boolean actual = book.matches(product, "Линус Торвальдс");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchesBookFalse() {

        boolean expected = false;
        Product product = item4;
        boolean actual = book.matches(product, "!Линус Торвальдс");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchesSmartphoneTrue() {

        Product product = item1;
        boolean actual = phone.matches(product, "PINE64");

        assertTrue(actual);
    }

    @Test
    public void shouldMatchesSmartphoneFalse() {

        Product product = item1;
        boolean actual = phone.matches(product, "1PINE64");

        assertFalse(actual);
    }


}
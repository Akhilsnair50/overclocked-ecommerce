package com.shopme.admin.user;

import com.shopme.admin.setting.SettingsRepository;
import com.shopme.common.entity.setting.Setting;
import com.shopme.common.entity.setting.SettingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class SettingRepositoryTests {
    @Autowired
    SettingsRepository settingsRepository;

    @Test
    public void testCreateGeneralSettings(){
        Setting siteName = new Setting("SITE_NAME", "Overclocked", SettingCategory.GENERAL);
        Setting siteLogo = new Setting("SITE_LOGO", "logosmall.png", SettingCategory.GENERAL);
        Setting copyright = new Setting("COPYRIGHT", "Copyright (C) 2023 Overclocked Ltd.", SettingCategory.GENERAL);

        settingsRepository.saveAll(List.of(siteName, siteLogo, copyright));

        Iterable<Setting> iterable = settingsRepository.findAll();

        assertThat(iterable).size().isGreaterThan(0);

    }
    @Test
    public void testCreateCurrencySettings() {
        Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
        Setting symbol = new Setting("CURRENCY_SYMBOL", "₹", SettingCategory.CURRENCY);
        Setting symbolPosition = new Setting("CURRENCY_SYMBOL_POSITION", "before", SettingCategory.CURRENCY);
        Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
        Setting decimalDigits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
        Setting thousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);

        settingsRepository.saveAll(List.of(currencyId, symbol, symbolPosition, decimalPointType,
                decimalDigits, thousandsPointType));

    }
    @Test
    public void testListSettingsByCategories(){
        List<Setting> settings = settingsRepository.findByCategory(SettingCategory.GENERAL);
        settings.forEach(System.out::println);
    }
}

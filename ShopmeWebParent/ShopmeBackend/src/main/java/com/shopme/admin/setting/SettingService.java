package com.shopme.admin.setting;

import com.shopme.common.entity.setting.Setting;
import com.shopme.common.entity.setting.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingsRepository settingsRepository;

    public List<Setting> listAllSettings(){
        return (List<Setting>) settingsRepository.findAll();
    }

    GeneralSettingBag getGeneralSettings(){
        List<Setting> settings = new ArrayList<>();

        List<Setting> generalSettings = settingsRepository.findByCategory(SettingCategory.GENERAL);
        List<Setting> currencySettings = settingsRepository.findByCategory(SettingCategory.CURRENCY);

        settings.addAll(generalSettings);
        settings.addAll(currencySettings);

        return new GeneralSettingBag(settings);


    }
    public void saveAll(Iterable<Setting> settings) {
        settingsRepository.saveAll(settings);
    }
    public List<Setting> getMailServerSettings() {
        return settingsRepository.findByCategory(SettingCategory.MAIL_SERVER);
    }

    public List<Setting> getMailTemplateSettings() {
        return settingsRepository.findByCategory(SettingCategory.MAIL_TEMPLATES);
    }
    public List<Setting> getCurrencySettings() {
        return settingsRepository.findByCategory(SettingCategory.CURRENCY);
    }
    public List<Setting> getPaymentSettings() {
        return settingsRepository.findByCategory(SettingCategory.PAYMENT);
    }
}

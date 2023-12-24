package com.shopme.admin.setting;

import com.shopme.common.entity.setting.Setting;
import com.shopme.common.entity.setting.SettingCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingsRepository extends CrudRepository<Setting,String> {
    public List<Setting> findByCategory(SettingCategory category);


}

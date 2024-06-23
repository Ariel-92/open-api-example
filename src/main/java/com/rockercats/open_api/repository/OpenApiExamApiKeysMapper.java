package com.rockercats.open_api.repository;

import com.rockercats.open_api.entity.ApiKeys;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpenApiExamApiKeysMapper {
    ApiKeys getApiKeys(String apiUuid);

    void addApiKey(ApiKeys apiKey);
}

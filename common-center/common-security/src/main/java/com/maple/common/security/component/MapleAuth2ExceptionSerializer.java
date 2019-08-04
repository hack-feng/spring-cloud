package com.maple.common.security.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.maple.common.security.exception.MapleAuth2Exception;
import lombok.SneakyThrows;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhua
 * @date 2019/7/30
 * <p>
 * OAuth2 异常格式化
 */
public class MapleAuth2ExceptionSerializer extends StdSerializer<MapleAuth2Exception> {
	public MapleAuth2ExceptionSerializer() {
		super(MapleAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(MapleAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		gen.writeStartObject();
		gen.writeObjectField("code", String.valueOf(value.getHttpErrorCode()));
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeStringField("path", request.getServletPath());
		gen.writeStringField("timestamp", String.valueOf(System.currentTimeMillis()));
		if (value.getAdditionalInformation()!=null) {
			for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
				String key = entry.getKey();
				String add = entry.getValue();
				gen.writeStringField(key, add);
			}
		}
		gen.writeEndObject();
	}
}

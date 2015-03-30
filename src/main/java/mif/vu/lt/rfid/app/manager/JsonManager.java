package mif.vu.lt.rfid.app.manager;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Getter @Setter
public class JsonManager implements ParseManager {

	private final ObjectMapper mapper;
	
	public JsonManager() {
		mapper = new ObjectMapper();
	}
	
	private <T> Object unmarshal(Class<?> clas, T  string) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(String.valueOf(string), clas);
	}

	@Override
	public <T> Object parse(Class<?> clas, T string) throws JsonParseException, JsonMappingException, IOException {
		return unmarshal(clas, string);
	}
	
}

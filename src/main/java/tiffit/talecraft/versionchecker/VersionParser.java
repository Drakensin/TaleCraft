package tiffit.talecraft.versionchecker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VersionParser {

	public static TCVersion getLatestVersion(){
		Gson gson = new Gson();
		try {
			InputStreamReader r = new InputStreamReader(new URL("https://widget.mcf.li/mc-mods/minecraft/242689-talecraft-a-mod-for-more-custom-and-advanced.json").openStream());
			JsonParser jsonParser = new JsonParser();
			JsonObject tc = (JsonObject) jsonParser.parse(r);
			JsonObject versions = tc.getAsJsonObject("versions");
			JsonArray v19 = versions.getAsJsonArray("1.9");
			String name = ((JsonObject)v19.get(0)).getAsJsonPrimitive("name").getAsString();
			TCVersion tcv = new TCVersion(name);
			return tcv;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

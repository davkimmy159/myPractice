package reference3RuntimeInjection;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Component
public class ExClass1 implements ExInterface1 {

	String field1;
	String field2;

	public ExClass1() {
	}

	public ExClass1(String field1, String field2) {
		this.field1 = field1;
		this.field2 = field2;
	}

	/*
	For use of placeholder, you need PropertySourcesPlaceholderConfigurer bean
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	*/
	public ExClass1(@Value("${ex.field1}") String field1) {
		this(field1, "");
	}

	/*
	#{3.141592}
	#{9.87E4}
	#{'Hello'}
	#{false}
	
	#{sgtPeppers.artist}
	#{artistSelector.selectArtist()}
	#{artistSelector.selectArtist().toUpperCase()}
	#{artistSelector.selectArtist()?.toUpperCase()} // If selectArtist() returns null, next method isn't called
	
	#{T(java.lang.Math)}
	#{T(java.lang.Math).PI}
	#{T(java.lang.Math).random()}
	#{T(System).currentTimeMillis()}
	
	#{2 * T(java.lang.Math).PI * circle.radius}
	#{T(java.lang.Math).PI * circle.radius ^ 2}
	#{disc.title + ' by ' + disc.artist}
	#{counter.total == 100}
	#{counter.total eq 100}
	#{scoreboard.score > 1000 ? "Winner!" : "Loser"}
	#{disc.title ?: 'Rattle and Hum'}
	
	#{admin.email matches '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com'}
	
	#{jukebox.songs[4].title}
	#{jukebox.songs[T(java.lang.Math).random() * jukebox.songs.size()].title}
	#{'This is a test'[3]}
	#{jukebox.songs.?[artist eq 'Aerosmith']} // filter and return new sub collection
	#{jukebox.songs.^[artist eq 'Aerosmith']} // filter and return first item
	#{jukebox.songs.$[artist eq 'Aerosmith']} // filter and return last item
	#{jukebox.songs.![title]} // return new collection of all item's property
	#{jukebox.songs.?[artist eq 'Aerosmith'].![title]}
	 */
	public ExClass1(@Value("#{systemProperties['ex.field1']}") Integer field1) {
		this(Integer.toString(field1), "");
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}
}

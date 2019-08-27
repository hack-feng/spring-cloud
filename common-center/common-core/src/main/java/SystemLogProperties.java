import com.maple.common.core.util.SystemLogAspect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({SystemLogAspect.class})
public class SystemLogProperties {

}

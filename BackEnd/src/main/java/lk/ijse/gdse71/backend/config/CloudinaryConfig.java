package lk.ijse.gdse71.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dbqxg7ey0",
                "api_key", "654195966286516",
                "api_secret", "HFUwr6MNRgZIRG-QQHL1_Hu8-R8",
                "secure", true
        ));
    }
}

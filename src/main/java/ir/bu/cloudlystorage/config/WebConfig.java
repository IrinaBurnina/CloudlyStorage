package ir.bu.cloudlystorage.config;

import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.repository.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class WebConfig implements WebMvcConfigurer {
    private final UsersRepository usersRepository;

    public WebConfig(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8081")  //-ЭТО АДРЕС ФРОНТА
                .allowedMethods("*");
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CustomParamResolverImplHandlerMethodArgumentResolver());
    }

    private class CustomParamResolverImplHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType().equals(CloudUser.class);
        }

        @Override
        public Object resolveArgument(MethodParameter parameter,
                                      ModelAndViewContainer mavContainer,
                                      NativeWebRequest webRequest,
                                      WebDataBinderFactory binderFactory) {
            String name = webRequest.getParameter("user");
            String password = webRequest.getParameter("password");
            return new CloudUser(name, password, null, true);
        }
    }
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.valueOf("Postgres"))
//                .addScript(JdbcDaoImpl.DEF_USERS_BY_USERNAME_QUERY)
//                .build();
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return login() - > usersRepository
                .getUserByLogin(login)
                .orElseThrow(() -> new NotFoundUser);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserAuthorizationFilter userAuthorizationFilter) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, "/hi", "/login", "/logout").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(Customizer.withDefaults())
                .addFilterBefore(userAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

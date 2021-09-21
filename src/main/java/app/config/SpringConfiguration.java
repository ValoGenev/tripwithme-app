package app.config;

import app.repository.*;
import app.service.application.ApplicationService;
import app.service.application.IApplicationService;
import app.service.car.CarService;
import app.service.car.ICarService;
import app.service.message.IMessageService;
import app.service.message.MessageService;
import app.service.notification.INotificationService;
import app.service.notification.NotificationService;
import app.service.rating.IRatingService;
import app.service.rating.RatingService;
import app.service.search.ISearchService;
import app.service.search.SearchService;
import app.service.trip.ITripService;
import app.service.trip.TripService;
import app.service.user.IUserService;
import app.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Autowired
    public IUserRepository userRepository;

    @Autowired
    public ITripRepository tripRepository;

    @Autowired
    public ISearchRepository searchRepository;

    @Autowired
    public ICarRepository carRepository;

    @Autowired
    public IMessageRepository messageRepository;

    @Autowired
    public INotificationRepository notificationRepository;

    @Autowired
    public IApplicationRepository applicationRepository;

    @Autowired
    public IRatingRepository ratingRepository;

    @Bean
    public IUserService userService(){
        return new UserService(userRepository,modelMapper());
    }

    @Bean
    public ITripService tripService(){
        return new TripService(tripRepository,userService(),modelMapper());
    }

    @Bean
    public ISearchService searchService(){
        return new SearchService(searchRepository, userService(), modelMapper());
    }

    @Bean
    public ICarService carService() {
        return new CarService(carRepository,userService(),modelMapper());
    }

    @Bean
    public IApplicationService applicationService(){
        return new ApplicationService(applicationRepository,userService(),tripService(),searchService(),modelMapper());
    }

    @Bean
    public IMessageService messageService(){
        return new MessageService(messageRepository,tripService(),userService(),modelMapper());
    }

    @Bean
    public INotificationService notificationService(){
        return new NotificationService(notificationRepository,userService(),modelMapper());
    }

    @Bean
    public IRatingService ratingService(){
        return new RatingService(ratingRepository,userService(),modelMapper());
    }


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

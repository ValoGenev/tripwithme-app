package app.util;

import app.dto.application.ApplicationAllPropertiesDto;
import app.dto.car.CarAllPropertiesDto;
import app.dto.notification.NotificationAllPropertiesDto;
import app.dto.rating.RatingAllPropertiesDto;
import app.dto.search.SearchAllPropertiesDto;
import app.dto.trip.TripAllPropertiesDto;

import java.util.List;

public interface Constants {



    // USERS CONSTANTS
    String FIND_USER_BY_ID_MESSAGE = "FINDING USER with ID: [%s].";
    String CREATE_USER_MESSAGE = "CREATING USER with USERNAME: [%s].";
    String UPDATE_USER_BY_ID_MESSAGE = "UPDATING USER with USERNAME: [%s].";
    String DELETE_USER_BY_ID_MESSAGE = "DELETING USER with USERNAME: [%s].";
    String GET_ALL_USERS_MESSAGE = "GETTING ALL USERS.";
    String GET_USER_TRIPS_MESSAGE = "GETTING TRIPS from USER with ID: [%s].";
    String GET_USER_SEARCHES_MESSAGE = "GETTING SEARCHES from USER with ID: [%s].";
    String GET_USER_CARS_MESSAGE = "Getting cars from user with id: [%s]";
    String GET_USER_APPLICATIONS_MESSAGE="Getting applications from user with id [%s]";
    String GET_USER_TRIPS_AS_DRIVER_MESSAGE = "Getting trips as driver from user with id: [%s]";
    String GET_USER_TRIPS_AS_PASSENGER_MESSAGE="Getting trips as passenger from user with id [%s]";
    String GET_USER_NOTIFICATIONS_MESSAGE="Getting notifications from user with id [%s]";
    String GET_USER_RATINGS_MESSAGE="Getting ratings from user with id [%s]";
    String CREATE_USER_BOT_MESSAGE = "CREATING BOT USER with USERNAME: [%s].";

    // CARS CONSTANTS
    String FIND_CAR_BY_ID_MESSAGE = "FINDING CAR with ID: [%s].";
    String CREATE_CAR_MESSAGE = "CREATING CAR with model: [%s].";
    String UPDATE_CAR_BY_ID_MESSAGE = "UPDATING CAR with ID: [%s].";
    String DELETE_CAR_BY_ID_MESSAGE = "DELETING CAR with ID: [%s].";
    String GET_ALL_CARS_MESSAGE = "GETTING ALL USERS.";

    // NOTIFICATION CONSTANTS
    String FIND_NOTIFICATION_BY_ID_MESSAGE = "FINDING NOTIFICATION with ID: [%s].";
    String CREATE_NOTIFICATION_MESSAGE = "CREATING NOTIFICATION with with message: [%s].";
    String UPDATE_NOTIFICATION_BY_ID_MESSAGE = "UPDATING NOTIFICATION with ID: [%s].";
    String DELETE_NOTIFICATION_BY_ID_MESSAGE = "DELETING NOTIFICATION with ID: [%s].";
    String GET_ALL_NOTIFICATIONS_MESSAGE = "GETTING ALL NOTIFICATIONS.";

    // TRIP CONSTANTS
    String FIND_TRIP_BY_ID_MESSAGE = "FINDING TRIP with ID: [%s].";
    String CREATE_TRIP_MESSAGE = "CREATING TRIP with DRIVER_USERNAME: [%s].";
    String UPDATE_TRIP_BY_ID_MESSAGE = "UPDATING TRIP with ID: [%s].";
    String DELETE_TRIP_BY_ID_MESSAGE = "DELETING TRIP with ID: [%s].";
    String GET_ALL_TRIPS_MESSAGE = "GETTING ALL TRIPS.";
    String ADD_PASSENGER_MESSAGE = "ADDING USER [%s] TO TRIP [%s]";
    String REMOVE_PASSENGER_MESSAGE = "ADDING USER [%s] TO TRIP [%s]";
    String GET_TRIP_MESSAGES_MESSAGE="GETTING TRIP [%s] messages";
    String GET_TRIP_APPLICATIONS_MESSAGE="GETTING TRIP [%s] applications";
    String GET_TRIP_PASSENGERS_MESSAGE="Getting trip [%s] passengers";

    // MESSAGE CONSTANTS
    String FIND_MESSAGE_BY_ID_MESSAGE = "FINDING MESSAGE with ID: [%s].";
    String CREATE_MESSAGE_MESSAGE = "CREATING MESSAGE WITH TEXT MESSAGE [%s].";
    String UPDATE_MESSAGE_BY_ID_MESSAGE = "UPDATING MESSAGE with ID: [%s].";
    String DELETE_MESSAGE_BY_ID_MESSAGE = "DELETING MESSAGE with ID: [%s].";
    String GET_ALL_MESSAGES_MESSAGE = "GETTING ALL MESSAGES.";

    // SEARCH CONSTANTS
    String FIND_SEARCH_BY_ID_MESSAGE = "FINDING SEARCH with ID: [%s].";
    String CREATE_SEARCH_MESSAGE = "CREATING SEARCH with USERNAME: [%s].";
    String UPDATE_SEARCH_BY_ID_MESSAGE = "CREATING SEARCH with ID: [%s].";
    String DELETE_SEARCH_BY_ID_MESSAGE = "DELETING SEARCH with ID: [%s].";
    String GET_ALL_SEARCHES_MESSAGE = "GETTING ALL SEARCHES.";

    // SEARCH CONSTANTS
    String FIND_TRIP_APPLICATION_BY_ID_MESSAGE = "FINDING TRIP_APPLICATION with ID: [%s].";
    String CREATE_TRIP_APPLICATION_MESSAGE = "CREATING TRIP_APPLICATION with MESSAGE: [%s].";
    String UPDATE_TRIP_APPLICATION_BY_ID_MESSAGE = "CREATING TRIP_APPLICATION with ID: [%s].";
    String DELETE_TRIP_APPLICATION_BY_ID_MESSAGE = "DELETING TRIP_APPLICATION with ID: [%s].";
    String GET_ALL_TRIP_APPLICATIONS_MESSAGE = "GETTING ALL TRIP_APPLICATIONS.";

    // RATING CONSTANTS
    String FIND_RATING_BY_ID_MESSAGE = "FINDING RATING with ID: [%s].";
    String CREATE_RATING_MESSAGE = "CREATING RATING with MESSAGE: [%s].";
    String UPDATE_RATING_BY_ID_MESSAGE = "CREATING RATING with ID: [%s].";
    String DELETE_RATING_BY_ID_MESSAGE = "DELETING RATING with ID: [%s].";
    String GET_ALL_RATINGS_MESSAGE = "GETTING ALL RATINGS.";

    // TRIP EXCEPTIONS CONSTANTS
    String TRIP_NOT_FOUND_MESSAGE = "Cannot find TRIP with ID [%s].";
    String DRIVER_AS_PASSENGER_MESSAGE = "Driver cannot be set as passenger";
    String NO_SEATS_AVAILABLE_MESSAGE="No more seats available for trip with id [%s]";
    String DRIVER_CANNOT_BE_CHANGED_MESSAGE="Driver cannot be changed";
    String PASSENGER_ALREADY_EXISTING_MESSAGE = "Passenger is already on the trip";


    // USER EXCEPTIONS CONSTANTS
    String USER_NOT_FOUND_MESSAGE = "Cannot find USER with id [%s].";

    // SEARCH EXCEPTIONS CONSTANTS
    String SEARCH_NOT_FOUND_MESSAGE="Cannot find SEARCH with id [%s].";

    // CAR EXCEPTIONS CONSTANTS
    String CAR_NOT_FOUND_MESSAGE="Cannot find CAR with id [%s].";

    // MESSAGE EXCEPTIONS CONSTANTS
    String MESSAGE_NOT_FOUND_MESSAGE = "Cannot find MESSAGE with id [%s].";

    //NOTIFICATION EXCEPTIONS CONSTANTS
    String NOTIFICATION_NOT_FOUND_MESSAGE = "Cannot find MESSAGE with id [%s]";

    //TRIP_APPLICATION EXCEPTIONS CONSTANTS
    String TRIP_APPLICATION_NOT_FOUND_MESSAGE="Cannot find TRIP_APPLICATION with id [%s]";

    //RATING EXCEPTIONS CONSTANTS
    String RATING_NOT_FOUND_MESSAGE="Cannot find RATING with id [%s]";

    // COMMON EXCEPTIONS CONSTANTS
    String DATABASE_ERROR_MESSAGE = "Database error occurred.";
    String NOT_FOUND_MESSAGE = "Resource was not found";
    String BAD_REQUEST_MESSAGE = "Invalid input was given";
    String CONFLICT_CREATE_MESSAGE = "Conflict while creating entity";
    String EXISTING_RESOURCE_MESSAGE = "Resource already exists.";
    String CONFLICT_DELETE_MESSAGE = "Entity  not allowed to be deleted";
    String UNAUTHORIZED_MESSAGE = "Unauthorized request was given.";
    String CONFIRM_PASS_DOES_NOT_MATCH_MESSAGE = "Confirm pass does not match user password.";
    String USERNAME_NOTE_EQUAL_MESSAGE = "Username cannot be changed.";
    String EMAIL_NOT_EQUAL_MESSAGE = "Email cannot be changed.";
    String ROLE_NOT_EQUAL_MESSAGE = "Role cannot be changed.";
    String EMAIL_NOT_FOUND_MESSAGE = "Email [%s] was not found";
    String EXISTING_EMAIL_MESSAGE="Email [%s] already exists.";

}

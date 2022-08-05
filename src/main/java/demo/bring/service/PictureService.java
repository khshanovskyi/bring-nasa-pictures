package demo.bring.service;

import com.bobocode.svydovets.annotation.Component;
import com.bobocode.svydovets.annotation.Inject;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.bring.entity.Photo;
import demo.bring.entity.Photos;
import demo.bring.repository.EntityRepository;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
@NoArgsConstructor
public class PictureService {

    @Inject("photoRepository")
    private EntityRepository<Photo> photoRepository;


    /**
     * Provides {@link List} with {@link Photo} saved on the DB.
     *
     * @return {@link List} with {@link Photo}
     */
    public List<Photo> getAllFromDB() {
        return photoRepository.getAll();
    }

    /**
     * Firstly is looking got in the DB, if in the DB no records with provided {@param sol} then searching url with
     * max 'content-length', save it to BD and return the result.
     *
     * @param sol number of the Mars sol
     * @return {@link String} with url to the largest picture
     */
    @SneakyThrows
    public String getLargestPictureUrl(String sol) {
        Photo photo = photoRepository.getById(Long.valueOf(sol));
        if (Objects.isNull(photo)) {
            photo = photoRepository.save(getLargestPhoto(sol));
        }

        return photo.getUrl();
    }

    @SneakyThrows
    private Photo getLargestPhoto(String sol) {
        var photos = getPhotos(sol).photos();
        var maxContentLengthUrl = photos.stream().parallel()
                .map(Photo::getUrl)
                .map(PictureService::getResponse)
                .map(httpResponse -> Pair.of(httpResponse.previousResponse().get().uri().toString(),
                        Long.parseLong(httpResponse.headers().firstValue("content-length").orElse("0"))))
                .max(Comparator.comparing(Pair::getRight))
                .map(Pair::getLeft)
                .orElseThrow();

        return photos.stream()
                .filter(photo -> photo.getUrl().equals(maxContentLengthUrl))
                .findFirst()
                .orElseThrow();
    }

    private static HttpResponse<String> getResponse(String url) {
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build()
                    .send(HttpRequest.newBuilder(URI.create(url)).build(),
                            HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    @SneakyThrows
    private Photos getPhotos(String sol) {
        var response = HttpClient.newBuilder().build()
                .send(HttpRequest.newBuilder(URI.create(generateUrl(sol))).GET().build(),
                        HttpResponse.BodyHandlers.ofString())
                .body();

        return new ObjectMapper().readValue(response, Photos.class);
    }

    private String generateUrl(String sol) {
        return "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=" + sol +
                "&api_key=E1qL4IdUk3v4DhiqY0RrukVQ68a2lxG7ua75KDiN";
    }

}

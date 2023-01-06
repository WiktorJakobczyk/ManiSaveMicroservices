package com.jakobczyk.BudgetAppArticles;

import com.jakobczyk.BudgetAppArticles.model.Article;
import com.jakobczyk.BudgetAppArticles.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootApplication
@EnableEurekaClient
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class BudgetAppArticlesApplication {
	private final ArticleRepository articleRepository;
	private final ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(BudgetAppArticlesApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public void initialMongoDbData() throws IOException {
		articleRepository.deleteAll();
		CommonsMultipartFile multipartFile = getImage("image_1.jpg");
		Article article = new Article(null, "a438cef2-fb09-4136-a949-87d271763a41", "Save 5 $ a day",
				"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)",
				new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()),
				LocalDateTime.now().toInstant(ZoneOffset.UTC), true, 5, "");
		articleRepository.save(article);

		multipartFile = getImage("image_2.jpg");
		Article article1 = new Article(null, "a438cef2-fb09-4136-a949-87d271763a41", "Make more money today!",
				"Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of de 'Finibus Bonorum et Malorum'(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, 'Lorem ipsum dolor sit amet..', comes from a line in section 1.10.32.",
				new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()),
				LocalDateTime.now().toInstant(ZoneOffset.UTC), true, 5, "");
		articleRepository.save(article1);

		multipartFile = getImage("image_3.jpg");
		Article article2 = new Article(null, "a438cef2-fb09-4136-a949-87d271763a41", "Save for life!",
				"There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.",
				new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()),
				LocalDateTime.now().toInstant(ZoneOffset.UTC), true, 5, "");
		articleRepository.save(article2);

		multipartFile = getImage("image_4.jpg");
		Article article3 = new Article(null, "a438cef2-fb09-4136-a949-87d271763a41", "Money saving apps",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. In blandit dapibus accumsan. Nam interdum nisl sit amet ipsum accumsan, nec condimentum massa sollicitudin. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque molestie orci elit, et mollis dui tristique pellentesque. Mauris fermentum nibh et lectus bibendum tempus. In id massa feugiat, congue odio ac, dapibus tellus. Nulla tempus leo neque, aliquet pulvinar elit ultrices sed.",
				new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()),
				LocalDateTime.now().toInstant(ZoneOffset.UTC), true, 5, "");
		articleRepository.save(article3);
	}


	private CommonsMultipartFile getImage(String name) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + name);
		File file = resource.getFile();
		FileItem fileItem = new DiskFileItemFactory().createItem("file",
				Files.probeContentType(file.toPath()), false, file.getName());
		try (InputStream in = new FileInputStream(file); OutputStream out = fileItem.getOutputStream()) {
			in.transferTo(out);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid file: " + e, e);
		}

		CommonsMultipartFile multipartFile = new CommonsMultipartFile(fileItem);
		return multipartFile;
	}

}

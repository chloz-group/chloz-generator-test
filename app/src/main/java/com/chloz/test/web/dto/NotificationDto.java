package com.chloz.test.web.dto;

import com.chloz.test.service.filter.UserFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;

	private String message;

	private String topic;

	private UserFilter userFilter;

	private Map<String, String> data;

}
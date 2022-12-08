package com.hong.blah.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplySaveRequestDto {

	private String user_id;
	private String board_id;
	private String content;
	private int bundle;
	private int step;
	private String opinion;
}

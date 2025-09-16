package com.booking.jwt.dto;

import com.booking.jwt.entity.Resource;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceDTO {

	private Long resourceId;
	private String name;
	private String type;
	private String description;
	private Integer capacity;
	private Boolean active;

	public Resource toEntity() {
        return Resource.builder()
                .resourceId(this.resourceId)
                .name(this.name)
                .type(this.type)
                .description(this.description)
                .capacity(this.capacity)
                .active(this.active != null ? this.active : true)
                .build();
    }

	public static ResourceDTO fromEntity(Resource r) {
		return ResourceDTO.builder()
				.resourceId(r.getResourceId())
				.name(r.getName())
				.type(r.getType())
				.description(r.getDescription())
				.capacity(r.getCapacity())
				.active(r.getActive())
				.build();
	}

}
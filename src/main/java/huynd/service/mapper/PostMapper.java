package huynd.service.mapper;

import huynd.domain.*;
import huynd.service.dto.PostDTO;

import huynd.service.dto.StoreGroupDTO;
import huynd.service.dto.StoreTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity Post and its DTO PostDTO.
 */
@Mapper(componentModel = "spring", uses = {StoreGroupMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "post.storeGroup.storeGroupID", target = "storeGroup.storeGroupID")
    default PostDTO toDto(Post post){
        if(post == null)
            return null;
        else{
            PostDTO postDTO = new PostDTO();
            postDTO.setPostID(post.getPostID());
            postDTO.setPostName(post.getPostName());
            postDTO.setPostTitle(post.getPostTitle());
            postDTO.setPostDescription(post.getPostDescription());
            postDTO.setPostStartDate(post.getPostStartDate());
            postDTO.setPostEndDate(post.getPostEndDate());
            postDTO.setPostBanner(post.getPostBanner());
            postDTO.setPostRemainNumberVoucher(post.getPostRemainNumberVoucher());
            postDTO.setPostTotalNumberVoucher(post.getPostTotalNumberVoucher());
            postDTO.setPostDiscountRate(post.getPostDiscountRate());
            if(post.getPostTotalNumberVoucher() != null){
                postDTO.setPostTotalNumberVoucher(post.getPostTotalNumberVoucher());
            }
            if(post.getPostRemainNumberVoucher() != null){
                postDTO.setPostRemainNumberVoucher(post.getPostRemainNumberVoucher());
            }
            if(post.getPostPointRequired() != null){
                postDTO.setPostPointRequired(post.getPostPointRequired());
            }
            if(post.getStoreGroup() != null){
                StoreGroupDTO storeGroupDTO = new StoreGroupDTO();
                storeGroupDTO.setStoreGroupID(post.getStoreGroup().getStoreGroupID());
                storeGroupDTO.setStoreGroupName(post.getStoreGroup().getStoreGroupName());
                storeGroupDTO.setStoreGroupPhone(post.getStoreGroup().getStoreGroupPhone());
                storeGroupDTO.setStoreGroupMail(post.getStoreGroup().getStoreGroupMail());
                storeGroupDTO.setStoreGroupWeb(post.getStoreGroup().getStoreGroupWeb());
                storeGroupDTO.setStoreGroupDescription(post.getStoreGroup().getStoreGroupDescription());
                storeGroupDTO.setStoreGroupLogo(post.getStoreGroup().getStoreGroupLogo());
                if(post.getStoreGroup().getStoreType() != null){
                    StoreTypeDTO storeTypeDTO = new StoreTypeDTO();
                    storeTypeDTO.setStoreTypeID(post.getStoreGroup().getStoreType().getStoreTypeID());
                    storeTypeDTO.setStoreTypeName(post.getStoreGroup().getStoreType().getStoreTypeName());
                    storeGroupDTO.setStoreType(storeTypeDTO);
                }
                postDTO.setStoreGroup(storeGroupDTO);
            }
            return postDTO;

        }
    }

    @Mapping(source = "storeGroupID", target = "storeGroup")
    @Mapping(target = "vouchers", ignore = true)
    default Post toEntity(PostDTO postDTO){
        if(postDTO == null)
            return null;
        else{
            Post post = new Post();
            if(postDTO.getPostID() != null)
                post.setPostID(postDTO.getPostID());
            post.setPostName(postDTO.getPostName());
            post.setPostTitle(postDTO.getPostTitle());
            post.setPostDescription(postDTO.getPostDescription());
            post.setPostStartDate(postDTO.getPostStartDate());
            post.setPostEndDate(postDTO.getPostEndDate());
            post.setPostBanner(postDTO.getPostBanner());
            post.setPostDiscountRate(postDTO.getPostDiscountRate());
            if(postDTO.getPostTotalNumberVoucher() != null){
                post.setPostTotalNumberVoucher(postDTO.getPostTotalNumberVoucher());
            }
            if(postDTO.getPostRemainNumberVoucher() != null){
                post.setPostRemainNumberVoucher(postDTO.getPostRemainNumberVoucher());
            }
            if(postDTO.getPostPointRequired() != null)
                post.setPostPointRequired(postDTO.getPostPointRequired());
            if(postDTO.getStoreGroup().getStoreGroupID() != null){
                StoreGroup storeGroup = new StoreGroup();
                storeGroup.setStoreGroupID(postDTO.getStoreGroup().getStoreGroupID());
                post.setStoreGroup(storeGroup);
            }

            return post;
        }
    }


    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setPostID(id);
        return post;
    }
}

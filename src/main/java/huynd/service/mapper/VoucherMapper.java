package huynd.service.mapper;

import huynd.domain.*;
import huynd.service.dto.PostDTO;
import huynd.service.dto.StoreGroupDTO;
import huynd.service.dto.VoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Voucher and its DTO VoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface VoucherMapper extends EntityMapper<VoucherDTO, Voucher> {

    @Mapping(source = "voucher.post.postId", target = "post.postID")
    default VoucherDTO toDto(Voucher voucher){
        if(voucher == null)
            return null;
        else{
            VoucherDTO voucherDTO = new VoucherDTO();
            if(voucher != null)
                voucherDTO.setVoucherID(voucher.getVoucherID());
            voucherDTO.setVoucherID(voucher.getVoucherID());
            voucherDTO.setVoucherNumber(voucher.getVoucherNumber());
            voucherDTO.setVoucherStatus(voucher.isVoucherStatus());
            voucherDTO.setVoucherPointRequired(voucher.getVoucherPointRequired());
            if(voucher.getPost().getPostID() != null){
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(voucher.getPost().getPostID());
                postDTO.setPostName(voucher.getPost().getPostName());
                if(voucher.getPost().getStoreGroup() != null){
                    StoreGroupDTO storeGroupDTO = new StoreGroupDTO();
                    storeGroupDTO.setStoreGroupName(voucher.getPost().getStoreGroup().getStoreGroupName());
                    postDTO.setStoreGroup(storeGroupDTO);
                }
                voucherDTO.setPost(postDTO);
            }
            return  voucherDTO;
        }
    }

    @Mapping(source = "voucherID", target = "post")
    default Voucher toEntity(VoucherDTO voucherDTO){
        if(voucherDTO == null)
            return null;
        else{
            Voucher voucher = new Voucher();
            if(voucherDTO.getVoucherID() != null)
                voucher.setVoucherID(voucherDTO.getVoucherID());
            voucher.setVoucherNumber(voucherDTO.getVoucherNumber());
            voucher.setVoucherStatus(voucherDTO.isVoucherStatus());
            if(voucherDTO.getPost().getPostID() != null){
                Post post = new Post();
                post.setPostID(voucherDTO.getPost().getPostID());
                voucher.setPost(post);
            }
            voucher.setVoucherPointRequired(voucherDTO.getVoucherPointRequired());
            return voucher;
        }
    }

    default Voucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        Voucher voucher = new Voucher();
        voucher.setVoucherID(id);
        return voucher;
    }
}

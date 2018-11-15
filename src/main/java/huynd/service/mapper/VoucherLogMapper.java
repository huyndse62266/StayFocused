package huynd.service.mapper;

import huynd.domain.*;
import huynd.service.dto.PostDTO;
import huynd.service.dto.UserDTO;
import huynd.service.dto.VoucherDTO;
import huynd.service.dto.VoucherLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VoucherLog and its DTO VoucherLogDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface VoucherLogMapper extends EntityMapper<VoucherLogDTO, VoucherLog> {

    @Mapping(source = "voucherLog.voucher.voucherID", target = "voucherID")
    @Mapping(source = "voucherLog.user.id", target = "id")
    default  VoucherLogDTO toDto(VoucherLog voucherLog){
        if(voucherLog == null){
            return  null;
        }else{
            VoucherLogDTO voucherLogDTO = new VoucherLogDTO();
            voucherLogDTO.setVoucherLogID(voucherLog.getVoucherLogID());
            voucherLogDTO.setVoucherLogUserUsed(voucherLog.getVoucherLogUserUsed());
            if(voucherLog.getVoucherLogDateUsed() != null)
                voucherLogDTO.setVoucherLogDateUsed(voucherLog.getVoucherLogDateUsed());
            if(voucherLog.getVoucher().getVoucherID() != null){
                VoucherDTO voucherDTO = new VoucherDTO();
                voucherDTO.setVoucherID(voucherLog.getVoucher().getVoucherID());
                voucherDTO.setVoucherNumber(voucherLog.getVoucher().getVoucherNumber());
                voucherLogDTO.setVoucher(voucherDTO);
                if(voucherLog.getVoucher().getPost() != null){
                    PostDTO postDTO = new PostDTO();
                    postDTO.setPostID(voucherLog.getVoucher().getPost().getPostID());
                    voucherDTO.setPost(postDTO);
                }
            }
            if(voucherLog.getUser() != null){
                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(voucherLog.getUser().getLogin());
                userDTO.setFirstName(voucherLog.getUser().getFirstName());
                userDTO.setLastName(voucherLog.getUser().getLastName());
                userDTO.setEmail(voucherLog.getUser().getEmail());
                userDTO.setPhone(voucherLog.getUser().getPhone());
                voucherLogDTO.setUser(userDTO);
            }
            return  voucherLogDTO;
        }
    }

    @Mapping(source = "voucherLogID", target = "user")
    @Mapping(source = "voucherLogID",target = "voucher")
    default VoucherLog toEntity(VoucherLogDTO voucherLogDTO){
        if(voucherLogDTO == null){
            return  null;
        }else{
            System.out.println("======>" +voucherLogDTO.getUser().getPhone());
            VoucherLog voucherLog = new VoucherLog();
            if(voucherLogDTO != null){
                voucherLog.setVoucherLogID(voucherLogDTO.getVoucherLogID());
            }
            voucherLog.setVoucherLogUserUsed(voucherLogDTO.getVoucherLogUserUsed());
            voucherLog.setVoucherLogDateUsed(voucherLogDTO.getVoucherLogDateUsed());
            if(voucherLogDTO.getVoucher() != null){
                Voucher voucher = new Voucher();
                voucher.setVoucherID(voucherLogDTO.getVoucher().getVoucherID());
                voucherLog.setVoucher(voucher);
            }
            if(voucherLogDTO != null){
                User user = new User();
                if(voucherLogDTO.getUser().getId() != null)
                    user.setId(voucherLogDTO.getUser().getId());
                voucherLog.setUser(user);
            }
            return  voucherLog;
        }
    }


    default VoucherLog fromId(Long  id) {
        if (id == null) {
            return null;
        }
        VoucherLog voucherLog = new VoucherLog();
        voucherLog.setVoucherLogID(id);
        return voucherLog;
    }
}

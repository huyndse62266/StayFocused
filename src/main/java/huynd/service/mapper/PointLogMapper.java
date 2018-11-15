package huynd.service.mapper;

import huynd.domain.*;
import huynd.service.dto.PointLogDTO;

import huynd.service.dto.UserDTO;
import huynd.service.dto.VoucherDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity PointLog and its DTO PointLogDTO.
 */
@Mapper(componentModel = "spring", uses = {VoucherMapper.class, UserMapper.class})
public interface PointLogMapper extends EntityMapper<PointLogDTO, PointLog> {

    @Mapping(source = "pointLog.voucher.voucherID", target = "voucherID")
    @Mapping(source = "pointLog.user.id", target = "id")
    default PointLogDTO toDto(PointLog pointLog){
        if(pointLog == null)
            return null;
        else{
            PointLogDTO pointLogDTO = new PointLogDTO();
            pointLogDTO.setPointLogID(pointLog.getPointLogID());
            pointLogDTO.setPointLogDateUsed(pointLog.getPointLogDateUsed());
            pointLogDTO.setPointLogPointSpent(pointLog.getPointLogPointSpent());
            if(pointLog.getVoucher() != null) {
                VoucherDTO voucherDTO = new VoucherDTO();
                voucherDTO.setVoucherID(pointLog.getVoucher().getVoucherID());
                pointLogDTO.setVoucher(voucherDTO);
            }
            if(pointLog.getUser().getId() != null){
                UserDTO userDTO = new UserDTO();
                userDTO.setId(pointLog.getUser().getId());
                pointLogDTO.setUser(userDTO);
            }
            return  pointLogDTO;
        }
    }

    @Mapping(source = "pointLogID", target = "user")
    @Mapping(source = "pointLogID",target = "voucher")
    default PointLog toEntity(PointLogDTO pointLogDTO){
        if(pointLogDTO == null)
            return null;
        else{
            PointLog pointLog = new PointLog();
            if(pointLogDTO.getPointLogID() != null)
                pointLog.setPointLogID(pointLogDTO.getPointLogID());
            pointLog.setPointLogID(pointLogDTO.getPointLogID());
            pointLog.setPointLogDateUsed(pointLogDTO.getPointLogDateUsed());
            pointLog.setPointLogPointSpent(pointLogDTO.getPointLogPointSpent());
            if(pointLogDTO.getVoucher().getVoucherID() != null){
                Voucher voucher = new Voucher();
                voucher.setVoucherID(pointLogDTO.getVoucher().getVoucherID());
                pointLog.setVoucher(voucher);
            }
            if(pointLogDTO.getUser().getId() != null){
                // Cái gì đây
                User user = new User();
                user.setId(pointLogDTO.getUser().getId());
                pointLog.setUser(user);
            }
            return  pointLog;
        }
    }

    default PointLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        PointLog pointLog = new PointLog();
        pointLog.setPointLogID(id);
        return pointLog;
    }
}

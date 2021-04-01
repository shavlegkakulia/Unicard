package ge.unicard.pos.networking.messaging.base;

public interface BaseResponseMapper<T extends BaseResponse, R> {

    R map(T response) throws Exception;
}

package ge.unicard.pos.networking.messaging.all_database;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class AllDatabaseMapper  implements BaseResponseMapper<AllDatabaseResponse, String> {

    @Override
    public String map(AllDatabaseResponse response) throws Exception {
        return response.displayText;
    }
}

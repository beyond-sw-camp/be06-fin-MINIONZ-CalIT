
package minionz.backend.error_board;

import minionz.backend.error_board.model.ErrorBoard;
import minionz.backend.error_board.model.ErrorBoardCategory;
import minionz.backend.error_board.model.ErrorBoardImage;
import minionz.backend.error_board.model.request.CreateErrorBoardRequest;
import minionz.backend.error_board.model.response.CreateErrorBoardResponse;
import minionz.backend.error_board.model.response.GetErrorBoardImageResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorBoardService {


    private final ErrorBoardRepository errorBoardRepository;
    private final ErrorBoardImageRepository errorBoardImageRepository;

    public ErrorBoardService(ErrorBoardRepository errorBoardRepository, ErrorBoardImageRepository errorBoardImageRepository) {
        this.errorBoardRepository = errorBoardRepository;
        this.errorBoardImageRepository = errorBoardImageRepository;
    }

public CreateErrorBoardResponse create( List<String> fileNames, CreateErrorBoardRequest dto)  {

    ErrorBoardCategory category = ErrorBoardCategory.valueOf(dto.getCategory().toUpperCase());
    ErrorBoard errorBoard = ErrorBoard.builder()
            .errorboardTitle(dto.getErrboardTitle())
            .errorboardContent(dto.getErrboardContent())
            .category(category)

            .build();
    errorBoardRepository.save(errorBoard);
    List<GetErrorBoardImageResponse> getPostImageResList = new ArrayList<>();
    for(String fileName : fileNames){
        ErrorBoardImage errorBoardImage = ErrorBoardImage.builder()
                .imageUrl(fileName)
                .errorBoard(errorBoard)
                .build();
        errorBoardImageRepository.save(errorBoardImage);
        GetErrorBoardImageResponse getPostImageRes = GetErrorBoardImageResponse.builder()
                .errorBoardImageId(errorBoardImage.getErrorBoardImageId())
                .imageUrl(errorBoardImage.getImageUrl())
                .createdAt(errorBoardImage.getCreatedAt())
                .modifiedAt(errorBoardImage.getModifiedAt())
                .build();
        getPostImageResList.add(getPostImageRes);
    }
    return CreateErrorBoardResponse.builder()
            .errorBoardId(errorBoard.getErrorBoardId())
            .errboardTitle(errorBoard.getErrorboardTitle())
            .errboardContent(errorBoard.getErrorboardContent())
            .errboardCategory(String.valueOf(errorBoard.getCategory()))
            .getErrorBoardImageResponsesList(getPostImageResList)
            .createdAt(errorBoard.getCreatedAt())
            .modifiedAt(errorBoard.getModifiedAt())
            .build();
}
}

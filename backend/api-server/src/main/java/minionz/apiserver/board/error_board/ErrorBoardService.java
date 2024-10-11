package minionz.apiserver.board.error_board;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.board.error_board.model.request.CreateErrorBoardRequest;
import minionz.apiserver.board.error_board.model.response.CreateErrorBoardResponse;
import minionz.apiserver.board.error_board.model.response.GetErrorBoardImageResponse;
import minionz.apiserver.board.error_board.model.response.GetErrorBoardResponse;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.workspace.WorkspaceRepository;
import minionz.common.board.error_board.model.ErrorBoard;
import minionz.common.board.error_board.model.ErrorBoardCategory;
import minionz.common.board.error_board.model.ErrorBoardImage;
import minionz.common.scrum.task.TaskRepository;
import minionz.common.scrum.task.model.Task;
import minionz.common.scrum.workspace.model.Workspace;
import minionz.common.user.UserRepository;
import minionz.common.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrorBoardService {
    private final ErrorBoardRepository errorBoardRepository;
    private final ErrorBoardImageRepository errorBoardImageRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public CreateErrorBoardResponse create(List<String> fileNames, CreateErrorBoardRequest request, Long workspaceId, Long userId)  {

        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND)); // 예외 처리

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WORKSPACE_NOT_EXISTS));
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.TASK_NOT_EXISTS));


        ErrorBoardCategory category = ErrorBoardCategory.valueOf(request.getCategory().toUpperCase());
        ErrorBoard errorBoard = ErrorBoard.builder()
                .errorboardTitle(request.getErrboardTitle())
                .errorboardContent(request.getErrboardContent())
                .category(category)
                .workSpace(workspace)
                .user(user1)
                .task(task)
                .build();

        errorBoardRepository.save(errorBoard);

        List<GetErrorBoardImageResponse> getPostImageResList = new ArrayList<>();
        for (String fileName : fileNames) {
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
                .workspaceId(errorBoard.getWorkSpace().getWorkspaceId())
                .userName(errorBoard.getUser().getUserName())
                .taskName(errorBoard.getTask().getTaskTitle())
                .build();
    }

    public GetErrorBoardResponse read(Long boardId) throws BaseException {

        ErrorBoard errorBoard = errorBoardRepository.findById(boardId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ERRORBOARD_SERACH_FAIL));


        List<ErrorBoardImage> errorBoardImageList = errorBoard.getErrorBoardImageList();
        List<GetErrorBoardImageResponse> getErrorBoardImageResponseList = new ArrayList<>();
        for (ErrorBoardImage errorBoardImage : errorBoardImageList) {
            GetErrorBoardImageResponse getErrorBoardImageResponse = GetErrorBoardImageResponse.builder()
                    .errorBoardImageId(errorBoardImage.getErrorBoardImageId())
                    .imageUrl(errorBoardImage.getImageUrl())
                    .createdAt(errorBoard.getCreatedAt())
                    .modifiedAt(errorBoard.getModifiedAt())
                    .build();
            getErrorBoardImageResponseList.add(getErrorBoardImageResponse);
        }
        return GetErrorBoardResponse.builder()
                .workspaceId(errorBoard.getWorkSpace().getWorkspaceId())
                .userName(errorBoard.getUser().getUserName())
                .errorBoardId(errorBoard.getErrorBoardId())
                .errboardTitle(errorBoard.getErrorboardTitle())
                .errboardContent(errorBoard.getErrorboardContent())
                .errboardCategory(String.valueOf(errorBoard.getCategory()))
                .getErrorBoardImageResponsesList(getErrorBoardImageResponseList)
                .createdAt(errorBoard.getCreatedAt())
                .modifiedAt(errorBoard.getModifiedAt())
                .workspaceId(errorBoard.getWorkSpace().getWorkspaceId())
                .taskName(errorBoard.getTask().getTaskTitle())
                .personaImage(errorBoard.getUser().getPersona())
                .build();
    }

    public Page<GetErrorBoardResponse> readAll(Long workspaceId,int page, int size)  {

        Page<ErrorBoard> result = errorBoardRepository.findAllByWorkspaceId(workspaceId,PageRequest.of(page, size));

        Page<GetErrorBoardResponse> getErrorBoardResponses = result.map(errorBoard-> {
            List<ErrorBoardImage> errorBoardImages = errorBoard.getErrorBoardImageList();
            List<GetErrorBoardImageResponse> getErrorBoardImageResponseList = new ArrayList<>();
            for (ErrorBoardImage errorBoardImage : errorBoardImages) {
                GetErrorBoardImageResponse getErrorBoardImageResponse = GetErrorBoardImageResponse.builder()
                        .errorBoardImageId(errorBoardImage.getErrorBoardImageId())
                        .imageUrl(errorBoardImage.getImageUrl())
                        .createdAt(errorBoardImage.getCreatedAt())
                        .modifiedAt(errorBoardImage.getModifiedAt())
                        .build();
                getErrorBoardImageResponseList.add(getErrorBoardImageResponse);
            }
            return GetErrorBoardResponse.builder()
                    .workspaceId(errorBoard.getWorkSpace().getWorkspaceId())
                    .userName(errorBoard.getUser().getUserName())
                    .errorBoardId(errorBoard.getErrorBoardId())
                    .errboardTitle(errorBoard.getErrorboardTitle())
                    .errboardContent(errorBoard.getErrorboardContent())
                    .errboardCategory(String.valueOf(errorBoard.getCategory()))
                    .getErrorBoardImageResponsesList(getErrorBoardImageResponseList)
                    .createdAt(errorBoard.getCreatedAt())
                    .modifiedAt(errorBoard.getModifiedAt())
                    .workspaceId(errorBoard.getWorkSpace().getWorkspaceId())
                    .taskName(errorBoard.getTask().getTaskTitle())
                    .personaImage(errorBoard.getUser().getPersona())
                    .build();
        });
        return getErrorBoardResponses;
    }
    public Page<GetErrorBoardResponse> readKeyword(Long workspaceId,String keyword, int page, int size) throws BaseException {

        Page<ErrorBoard> result = errorBoardRepository.findByKeyword(workspaceId,keyword, PageRequest.of(page, size))
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ERRORBOARD_SERACH_FAIL));
        Page<GetErrorBoardResponse> getErrorBoardResponses = result.map(errorBoard-> {
            List<ErrorBoardImage> errorBoardImages = errorBoard.getErrorBoardImageList();
            List<GetErrorBoardImageResponse> getErrorBoardImageResponseList = new ArrayList<>();
            for (ErrorBoardImage errorBoardImage : errorBoardImages) {
                GetErrorBoardImageResponse getErrorBoardImageResponse = GetErrorBoardImageResponse.builder()
                        .errorBoardImageId(errorBoardImage.getErrorBoardImageId())
                        .imageUrl(errorBoardImage.getImageUrl())
                        .createdAt(errorBoard.getCreatedAt())
                        .modifiedAt(errorBoard.getModifiedAt())
                        .build();
                getErrorBoardImageResponseList.add(getErrorBoardImageResponse);
            }
            return GetErrorBoardResponse.builder()
                    .workspaceId(errorBoard.getWorkSpace().getWorkspaceId())
                    .userName(errorBoard.getUser().getUserName())
                    .errorBoardId(errorBoard.getErrorBoardId())
                    .errboardTitle(errorBoard.getErrorboardTitle())
                    .errboardContent(errorBoard.getErrorboardContent())
                    .errboardCategory(String.valueOf(errorBoard.getCategory()))
                    .getErrorBoardImageResponsesList(getErrorBoardImageResponseList)
                    .createdAt(errorBoard.getCreatedAt())
                    .modifiedAt(errorBoard.getModifiedAt())
                    .workspaceId(errorBoard.getWorkSpace().getWorkspaceId())
                    .taskName(errorBoard.getTask().getTaskTitle())
                    .personaImage(errorBoard.getUser().getPersona())
                    .build();
        });
        return getErrorBoardResponses;
    }
    public Page<GetErrorBoardResponse> readCategory(Long workspaceId,String categoryString, int page, int size) throws BaseException {

        ErrorBoardCategory category;
        try {
            category = ErrorBoardCategory.valueOf(categoryString.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new BaseException(BaseResponseStatus.ERRORBOARD_SERACH_FAIL);
        }

        Page<ErrorBoard> result = errorBoardRepository.findByCategory(category,workspaceId, PageRequest.of(page, size));


        Page<GetErrorBoardResponse> getErrorBoardResponses = result.map(errorBoard -> {
            List<ErrorBoardImage> errorBoardImages = errorBoard.getErrorBoardImageList();
            List<GetErrorBoardImageResponse> getErrorBoardImageResponseList = new ArrayList<>();


            for (ErrorBoardImage errorBoardImage : errorBoardImages) {
                GetErrorBoardImageResponse getErrorBoardImageResponse = GetErrorBoardImageResponse.builder()
                        .errorBoardImageId(errorBoardImage.getErrorBoardImageId())
                        .imageUrl(errorBoardImage.getImageUrl())
                        .createdAt(errorBoardImage.getCreatedAt())
                        .modifiedAt(errorBoardImage.getModifiedAt())
                        .build();
                getErrorBoardImageResponseList.add(getErrorBoardImageResponse);
            }

            // GetErrorBoardResponse로 변환하여 리턴
            return GetErrorBoardResponse.builder()
                    .workspaceId(errorBoard.getWorkSpace().getWorkspaceId())
                    .userName(errorBoard.getUser().getUserName())
                    .errorBoardId(errorBoard.getErrorBoardId())
                    .errboardTitle(errorBoard.getErrorboardTitle())
                    .errboardContent(errorBoard.getErrorboardContent())
                    .errboardCategory(String.valueOf(errorBoard.getCategory()))
                    .getErrorBoardImageResponsesList(getErrorBoardImageResponseList)
                    .createdAt(errorBoard.getCreatedAt())
                    .modifiedAt(errorBoard.getModifiedAt())
                    .workspaceId(errorBoard.getWorkSpace().getWorkspaceId())
                    .taskName(errorBoard.getTask().getTaskTitle())
                    .personaImage(errorBoard.getUser().getPersona())
                    .build();
        });

        return getErrorBoardResponses;
    }

}
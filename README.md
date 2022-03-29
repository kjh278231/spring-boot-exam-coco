#1 Address 전체 조회 (GET /addresses)
## 1.1 AddressService.getAddressList 전체 조회 구현 (TODO 1.1)
- 전체 Address 정보는 AddressRepository를 통하여 조회 한다.
- AddressServiceTest.getAddressList 테스트가 통과 되도록 한다.

## 1.2 AddressController.retrieveAddressList 전체 조회 구현 (TODO 1.2)
- AddressService.getAddressList를 호출한다.
- AddressControllerTest.retrieveAddressList 테스트가 통과 되도록 한다. 

#2 Address 단건 조회 (GET /addresses/{id})
## 2.1 AddressService.getAddress와 단건 조회 구현 (TODO 2.1)
- 입력받은 id에 해당하는 Addresss 정보는 AddressRepository를 통하여 조회 한다.
- 입력받은 id에 해당하는 address 정보가 없을 경우 AddressNotFoundException 예외가 발생하도록 구현한다.
- 에러 형식은 99 id에 해당하는 정보가 없을 경우 "99 not found"라고 출력한다.
- AddressServiceTest.getAddress와 getAddress_thenThrowAddressNotFoundException 테스트가 통과 되도록 한다.

## 2.2 AddressController.retrieveAddress 단건 조회 구현 (TODO 2.2)
- AddressService.getAddress를 호출한다.
- 전체조회를 할 수 있는 링크를 생성하고 (ControllerLinkBuilder 또는 WebMvcLinkBuilder) (링크 명은 all-address)
- EntityModel<T> 또는 Resource<T>에 조회된 Address를 담아서 리턴한다.
- AddressControllerTest.retrieveAddress 테스트가 통과 되도록 한다.

#3 Address 저장 (POST /addresses)
## 3.1 AddressService.saveAddress 저장 구현 (TODO 3.1)
- 입력받은 Address 정보를 AddressRepository를 통하여 저장한다.
- AddressServiceTest.saveAddress 테스트가 통과 되도록 한다.

## 3.2 AddressController.saveAddress 저장 구현 (TODO 3.2)
- 입력받은 Address 정보를 AddressService.saveAddress를 호출 한다.
- ResponseEntity를 리턴한다.
- ResponseEntity에는 저장 된 Address 정보를 호출 할 수 있는 URI 정보가 포함 된다.
- ResponseEntity에는 201(created) HttpStatus가 포함 된다.
- AddressControllerTest.saveAddress 테스트가 통과 되도록 한다.

#4 Address 삭제 (DELETE /addresses/{id})
## 4.1 AddressService.deleteAddress 삭제 구현 (TODO 4.1)
- 입력받은 id에 해당하는 Address 정보를 AddressRepository를 통하여 삭제 한다.
- AddressServiceTest.deleteAddress 테스트가 통과 되도록 한다.

## 4.2 AddressController.deleteAddressr 삭제 구현 (TODO 4.2)
- 입력받은 id와 함께 AddressSerevice.deleteAdderess를 호출 한다.
- AddressControllerTest.deleteAddress 테스트가 통과 되도록 한다.  

#5 Address 조건 조회 (GET /addresses?searchKeyword=)
## 5.1 AddressService.getAddressBySearchKeyword 조건 조회 구현 (TODO 5.1)
- 입력받은 searchKeyword에 해당하는 Address 정보를 AddressRepository.findAllBySearchKeyword를 통하여 조회 한다.
- AddressServiceTest.getAddressBySearchKeyword 테스트가 통과 되도록 한다.

## 5.2 AddressController.retrieveAddressListBySearchKeyword 조건 조회 구현 (TODO 5.2)
- searchKeyword라는 param을 받기 위해 @GetMapping에 params를 구현한다.
- @RequestParam으르 통하여 searchKeyword를 받아서 AddressService.getAddressListBySearchKeyword를 호출 한다.
- AddressControllerTest.getAddressBySearchKeyword 테스트가 통과 되도록 한다.

#6 AddressExceptionHandler 구현 (AOP)
- 예외 발생시 필요한 예외정보만 담을 ExceptionResponse를 리턴하기 위하여 AddressExceptionHandler를 구현한다.
## 6.1 AddressExceptionHandler.handleAddressNotFoundException 구현 (TODO 6.1)
- AddressNotFoundException이 발생 하였을 때 현재시간, 에러 메시지, 리퀘스트 정보를 담은 ExceptionResponse 객체를 생성한다.
- ResponseEntity에 위에서 생성한 ExceptionResponse와 HttpStatus.NOT_FOUND를 담아서 리턴한다.
- AddressControllerTest.addressNotFoundExceptionTest 테스트가 통과 되도록 한다.

## 6.2 AddressExceptionHandler.handleMethodArgumentNotValid 재정의 (TODO 6.2)
- MethodArgumentNotValidException이 발생 하였을 때 현재시간, 에러 메시지, 리퀘스트 정보를 담은 ExceptionResponse 객체를 생성한다.
- ResponseEntity에 위에서 생성한 ExceptionResponse와 HttpStatus.INTERNAL_SERVER_ERROR를 담아서 리턴한다.
- AddressControllerTest.handleMethodArgumentNotValidTest 테스트가 통과 되도록 한다.

# 7. 보너스 문제
## 7.1 이메일 체크 이후 저장 
- AddressService.saveAddress를 수정하고 AddressExceptionHandler.handleAlreadyExistEmailException을 구현한다.
- 새로운 사용자를 추가 하려고 할 때 이미 존재하는 이메일이면 AlreadyExistEmailException 에러를 발생 시킨다.
- 해당 에러의 HttpStatus는 409 이다.
- AddressControllerTest.handleAlreadyExistEmailExceptionTest 테스트가 통과 되도록 한다.

## 7.2 CellPhone
- id(Integer), carrier(String), cellPhoneNo(String)을 가진 CellPhone entity를 만든다.
- Address와 CellPhone 의 관계는 1:n 이다.
- address id에 해당하는 모든 핸드폰 번호를 리턴하는 GET /addresses/{id}/cell-phones REST API를 구현한다.
- address id에 새로운 핸드폰 번호를 저장하는 POST /addresses/{id}/cell-phones REST API를 구현한다.
- POST의 경우 address/{id} 에 이미 존재하는 핸드폰 번호일 경우 AlreadyExistCellPhoneException 예외를 발생 한다.

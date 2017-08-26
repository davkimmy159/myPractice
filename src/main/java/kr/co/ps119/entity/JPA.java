package kr.co.ps119.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JPA {

	@PersistenceContext
	private EntityManager em;
	/*
	public void queryDSL() {
		JPAQuery<Object> query = new JPAQuery<>(em);
//		QMember qMember = new QMember("m");
	}
	*/
}

/*

@Entity
// 1- 조인전략(각각 테이블)
@Inheritance(Strategy = InheritanceType.JOINED)
// 2- 단일 테이블
@Inheritance(Strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;
	
	private String name;
	private int price;
}
@Entity
@DiscriminatorValue("A")
// @PrimaryKeyJoinColumn(name = "ALBUM_ID") (JOIN 전략때만 가능)
public class Album extends Item {
	private String artist;
}

------------------------------

@MappedSuperClass
public abstract class BaseEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
}
@Entity
@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID"))
@AttributeOVerrides({
	@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID")),
	@AttributeOverride(name = "name", column = @Column(name = "MEMBER_NAME")),
})
public class Member extends BaseEntity {
	// id 상속
	// name 상속
	private String email;
}
@Entity
public class Seller extends BaseEntity {
	// id 상속
	// name 상속
	private String shopName;
}

--------------------------------

// A. 비식별 관계
// 복합키는 GeneratedValue 사용 불가
// 1. @IdClass
@Entity
@IdClass(ParentId.class)
public class Parent {
	@Id
	@Column(name = "PARENT_ID1")
	private String id1;
	@Id
	@Column(name = "PARENT_ID2")
	private String id2;
}
public class ParentId implement Serializable {
	private String id1;
	private String id2;
	public ParentId() {}
	public ParentId(String id1, String id2) {
		this.id1 = id1;
		this.id2 = id2;
	}
	@Override
	public boolean equals(Object o) {...}
	@OVerride
	public int hashCode() {...}
}
Parent parent = new Parent();
parent.setId1("myId1");
parent.setId2("myId2");
em.persist(parent);

// 2. @EmbeddedId
@Entity
public class Parent {
	@EmbeddedId
	private ParentId id;
}
@Embeddable
public class ParentId implement Serializable {
	@Column(name = "PARENT_ID1")
	private String id1;
	@Column(name = "PARENT_ID2")
	private String id2;
	public ParentId() {}
	public ParentId(String id1, String id2) {
		this.id1 = id1;
		this.id2 = id2;
	}
	@Override
	public boolean equals(Object o) {...}
	@OVerride
	public int hashCode() {...}
}
Parent parent = new Parent();
ParentId parentId = new ParentId("myId1", "myId2");
parent.setId(parentId);
em.persist(parent);

@Entity
public class Child {
	@Id
	private String id;
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "PARENT_ID1", refrencedColumnName = "PARENT_ID1"),
		@JoinColumn(name = "PARENT_ID2") --> 이름이 같으면 생략 가능
	})
	private Parent parent;
}

------------------------------

// B. 식별 관계
@Entity
// 복합키는 GeneratedValue 사용 불가
// 1. @IdClass
public class Parent {
	@Id
	@GeneratedValue
	@Column(name = "PARENT_ID")
	private String id;
}
@Entity
@IdClass(ChildId.class)
public class Child {
	@Id
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	public Parent parent;
	@Id
	@Column(name = "CHILD_ID")
	private String childId;
}
public class ChildId implements Serializable {
	private String parent;
	private String childId;
	public ChildId() {}
	public ChildId(String parent, String childId) {
		this.parent = parent;
		this.childId = childId;
	}
	@Override
	public boolean equals(Object o) {...}
	@OVerride
	public int hashCode() {...}
}
@Entity
@IdClass(GrandChildId.class)
public class GrandChild {
	@Id
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "PARENT_ID"),
		@JoinColumn(name = "CHILD_ID")
	})
	private Child child;
	@Id
	@Column(name = "GRANDCHILD_ID")
	private String id;
}
public class GrandChildId implements Serializable {
	private ChildId child;
	private String id;
	public GrandChildId() {}
	public GrandChildId(ChildId child, String id) {
		this.ChildId = ChildId;
		this.id = id;
	}
	@Override
	public boolean equals(Object o) {...}
	@OVerride
	public int hashCode() {...}
}

// 2. @EmbeddedId
@Entity
public class Parent {
	@Id
	@GeneratedValue
	@Column(name = "PARENT_ID")
	private String id;
}
@Entity
public class Child {
	@EmbeddedId
	private ChildId id;
	@MapsId("parentId")
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	public Parent parent;
}
@Embeddable
public class ChildId implements Serializable {
	private String parentId;
	@Column(name = "CHILD_ID")
	private String id;
	public ChildId() {}
	public ChildId(String parentId, String id) {
		this.parentId = parentId;
		this.id = id;
	}
	@Override
	public boolean equals(Object o) {...}
	@OVerride
	public int hashCode() {...}
}
@Entity
public class GrandChild {
	@EmbeddedId
	private GrandChildId id;
	
	@MapsId("childId")
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "PARENT_ID"),
		@JoinColumn(name = "CHILD_ID")
	})
	private Child child;
}
@Embeddable
public class GrandChildId implements Serializable {
	private ChildId childId;
	@Column(name = "GRANDCHILD_ID")
	private String id;
	public GrandChildId() {}
	public GrandChildId(ChildId childId, String id) {
		this.childId = childId;
		this.id = id;
	}
	@Override
	public boolean equals(Object o) {...}
	@OVerride
	public int hashCode() {...}
}

-------------------------

// 일대일 식별
@Entity
public class Board {
	@Id
	@GeneratedValue
	@Column(name = "BOARD_ID")
	private Long id;
	@OneToOne(mappedBy = "board")
	private BoardDetail boardDetail;
}
@Entity
public class BoardDetail {
	@Id
	private Long boardId;
	@MapsId
	@OneToOne
	@JoinColumn(name = "BOARD_ID")
	private Board board;
}

------------------------------

NULL 제약조건과 JPA 조인 전략 (외부조인 -> 내부조인)
	@JoinColumn 					-> 외부조인 사용 (default : nullable = true) 
	@JoinColumn(nullable = false) 	-> 내부조인 사용
or
	@ManyToOne(fetch = FetchType.EAGER) 					-> 외부조인 사용  (default : optional = true) 
	@ManyToOne(fetch = FetchType.EAGER, optional = false) 	-> 내부조인 사용

-----------------------------

@Entity
public class Member {
	@Embedded
	Address address;
	@Embedded
	PhoneNumber phoneNumber;
}
@Embeddable
public class Address {
	String street;
	String city;
	String state;
	@Embedded
	Zipcode zipcode;
}
@Embeddable
public class Zipcode {
	String zip;
	String plusFour;
}
@Entity
public class PhoneServiceProvider {
	@Id
	String name;
}
@Embeddable
public class PhoneNumber {
	String areaCode;
	String localNumber;
	@ManyToOne // 엔티티 참조
	PhoneServiceProvider provider
}

 @Entity
 public class Member {
 	@Id
 	@GeneratedValue
 	private Long id;
 	private String name;
 	@Embedded
 	Address homeAddress;
 	@Embedded
 	@AttributeOverrides({ // 항상 entity 에 설정 (Embeddable 이 Embeddable 을 가지고 있어도)
 		@AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
 		@AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET")),
 		@AttributeOverride(name = "zipcode", column = @Column(name = "COMPANY_ZIPCODE"))
 	})
 	Address companyAddress;
}

@Embeddable 은 가능하면 불변객체로 만들자 -> setter (X)

-----------------------------------


JPQL, HQL 	->	JPQL 로 조회한 Entity 는 영속성 컨텍스트에서 관리된다
				JPQL 로 조회한 Embedded 는 영속성 컨텍스트에서 관리되지 않는다

TypedQuery<Member> typedQuery = em.createQuery("SELECT m FROM Member m", Member.class);
Query query = em.createQuery("SELECT m FROM Member m");
List resultList = query.getResultList();
for (Object o : resultList) {
	Object[] result = (Object[]) o; // 조회 필드가 둘 이상이면 Object[], 하나면 Object 반환
	System.out.println("username = " + result[0]);
	System.out.println("age = " + result[1]);
}
Query query = em.createQuery("SELECT o.orderName, o.member FROM Order o"); // 여러 값(엔티티 같이 가능)을 선택하면 TypedQuery 사용 불가능
List<Object[]> resultList = query.getResultList();

String usernameParam = "Userl";
List<Member> members = em.createQuery("SELECT m FROM m WHERE m.username = :username", Member.class)
						 .setParameter("username", usernameParam)
						 // .setParameter(1, usernameParam)
						 .getResultList();

TypedQuery<UserDTO> query = em.createQuery("SELECT new example.jpql.UesrDTO(m.username, m.age) FROM Member m", UserDTO.class);

fetch 조인은 별칭 사용 불가능 (하이버네이트는 가능)
select m from Member m join fetch m.team (지연로딩 발생 X)
select distinct t from Team t join fetch t.members where t.name = '팀A' (distinct 는 일대다 fetch 조인에서 중복되는 엔티티(일)를 어플리케이션에서 제거함)

-------------------------------------------

Spring data JPA
벌크성 수정, 삭제 쿼리
@Modifying
@Query("update Product p set p.price = p.price * 1.1 where p.stockAmount < :stockAmount")
int bulkPriceUp(@Param("stockAmount") String stockAmount);

// count 쿼리 사용
Page<Member> findByName(String name, Pageable pageable);
// count 쿼리 미사용
List<Member> findByName(String name);
List<Member> findByName(String name, Pageable pageable);

PageRequest pageRequest = nwe PageRequest(0, 10, new Sort(Direction.DESC, "name"));
Page<Member> result = memberRepository.findByNameStartingWith("김", pageRequest);

@QueryHints(value = {@QueryHint(name = "org.hibernate.readOnly", value = "true")}, forCounting = true)
Page<Member> findByName(String name, Pageable pageable);

// 커스텀 리파지토리
public interface MemberRepositoryCustom {
	public List<Member> findMemberCuston();
}
public class MemberRepositoryImpl implements MemberRepositoryCustom {
	@Override
	public List<Member> findMemberCustom() {}
}
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {}

@EnableSpringDataWebSupport
DomainClassConverter
HandlerMethodArgumentResolver

DomainClassConverter 기능
@Controller
public class MemberController {
	@Autowired
	MemberRepository memberRepository;
	
	// DomainClassConverter 미적용
	@RequestMapping("member/memberUpdateForm")
	public String memberUpdateForm(@RequestParam("id") Long id, Model modle) {
		Member member = memberRepository.findOne(id);
		model.addAttribute("member", member);
		return "member/memberSaveForm");
	}
	
	// DomainClassConverter 적용
	@RequestMapping("member/memberUpdateForm")
	public String memberUpdateForm(@RequestParam("id") Member member, Model modle) {
		model.addAttribute("member", member);
		return "member/memberSaveForm");
	}
}

HandlerMethodArgumentResolver 기능
(/members?page=0&size=20&sort=name,desc&sort=address.city)
@RequestMapping(value = "/members", method = RequestMapping.GET)
public String list(@PageableDefault(size = 12, sort = "name", direction = Sort.Direction.DESC) Pageable pageable,
				   @Qualifier("member") Pageable memberPageable,
				   @Qualifier("order") Pageable orderPageable,
				   Model model) {
	Page<Member> page = memberService.findMembers(pageable);
	model.addAttribute("members", page.getContent());
	return "members/memberList";
}
PageableHandlerMethodArgumentResolver.setOneIndexedParemeters(true);

---------------------------------------

Spring Data JPA + QueryDSL

org.springframework.data.querydsl.QueryDslPredicateExecutor
org.springframework.data.querydsl.QueryDslRepositorySupport

QueryDslPredicateExecutor
public interface ItemRepository extends JpaRepository<Item, Long>, QueryDslPredicateExecutor<Item> {}
QItem item = QItem.item;
Iterable<Item> result = itemRepository.findAll(item.name.contains("장난감").and(item.price.between(10000, 20000)));

QueryDslRepositorySupport
public interface CustomOrderRepository {
	public List<Order> search(OrderSearch orderSearch);
}
public class OrderRepositoryImple extends QueryDslRepositorySupport implements CustomOrderRepository {
	public OrderRepositoryImpl() {
		super(Order.class);
	}
	
	@Override
	public List<Order> search(Ordersearch orderSearch) {
		QOrder order = QOrder.order;
		QMember member = QMember.member;
		
		JPQLQuery query = from(order);
		
		if (StringBuilder.hasText(orderSearch.getMemberName())) {
			query.leftJoin(order.member, member)
				 .where(member.name.contains(orderSearch.getMembername()));
		}
		
		if (orderSearch.getOrderStatus() != null) {
			query.where(order.status.eq(orderSearch.getOrderStatus()));
		}
		
		return query.list(order);
	}
}

--------------------------------------------------

@OneToMany(mappedBy = "taem")
@OrderBy("username desc, id asc")
private Set<Member> members = new HashSet<>();

@Entity
// 클래스 적용
@Convert(converter = BooleanToYNConverter.class, attributeName = "vip")
public class Member {
	// 필드 적용
	@Convert(converter = BooleanToYNConverter.class)
	private boolean vip;
}
@Converter
// @Converter(autoApply = true) -> 글로벌 적용
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {
	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		return (attribute != null && attribute) ? "Y" : "N";
	}
	@Override
	public String convertToEntityAttribute(String dbData) {
		return "Y".equals(dbData);
	}
}

@EnableJpaAuditing

@Entity
@EntityListeners(DuckListener.class)
public class Duck {}
public class DuckListener {}

@ExcludeDefaultListeners
@ExcludeSupderclassListeners

@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class TheEntity {}
public class AuthAndDateAuditorAware implements AuditorAware<String> {
	@Override
	public String getCurrentAuditor() {
		return "userId";
	}
}
@CreatedDate // 처음 entity가 저장될때 생성일을 주입해준다.
@CreatedBy	// 생성자 주입 
@LastModifiedDate	// entity가 수정될때 수정일자를 주입해준다. 
@LastModifiedBy	// 수정자 주입 

@NamedEntityGraph(name = "Order.withMember", attributeNodes = { @NamedAttributeNode("member")})
@Entity
public class Order {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
}
EntityGraph graph = em.getEntityGraph("Order.withMember");
Map hints = new HashMap();
hints.put("javax.persistence.fetchgraph", graph);
Order order = em.find(Order.class, orderId, hints);
}

Order -> OrderItem -> Item
@NamedEntityGraph(name = "Order.withAll",
				  attributeNodes = {
				  	@NamedAttributeNode("Member"),
				  	@NamedAttributeNode(value = "orderItems", subgraph = "orderItems")
			  	  },
			  	  subgraphs = @NamedSubgraph(name = "orderItems",
											 attributeNodes = {
											 	@NamedAttributeNode("item")
									 		 })
)
@Entity
public class Order {}
EntityGraph graph = em.getEntityGraph("Order.withAll");
Map hints = new HashMap();
hints.put("javax.persistence.fetchgraph", graph);
Order order = em.find(Order.class orderId, hints);

List<Order> resultList = em.createQuery("select o from Order o where o.id = :orderId", Order.class)
						   .setParameter("orderId", orderId)
						   .setHint("javax.persistence.fetchgraph", em.getEntityGraph("Order.withAll"))
						   .getResultList();

EntityGraph<Order> graph = em.craeteEntityGraph(Order.class);
graph.addAttributeNodes("member");
Map hints = new HashMap();
hints.put("javax.persistence.fetchgraph", graph);
Order order = em.find(Order.class, orderId, hints);

EntityGraph<Order> graph = em.craeteEntityGraph(Order.class);
graph.addAttributeNodes("member");
Subgraph<OrderItem> orderItems = graph.addSubgraph("orderItems");
orderItems.addAttributeNodes("item");
Map hints = new HashMap();
hints.put("javax.persistence.fetchgraph", graph);
Order order = em.find(Order.class, orderId, hints);

----------------------------------

public PersistenceExceptionTranslationProcessor exceptionTranslation() {
	return new PersistenceExceptionTranslationProcessor)_;
}

N + 1
@BatchSize(size = 5)
<property name="hibernate.default_batch_fetch_size" value="5" />
@Fetch(FetchMode.SUBSELECT)

TypedQuery<Order> query = em.createQuery("select o from Order o", Order.class);
query.setHint("org.hibernate.readOnly", true);
@Transactional(readOnly = true)
@Transactional(propagation = Propagation.NOT_SUPPORTED)

<property name="hibernate.hibernate.jdbc.batch_size" value="50" />

---------------------------------

@Version (적용가능 : Long(long), Integer(int) Short(short), Timestamp
@Entity
public class Board {
	@Id
	private String id;
	private String title;
	
	@Version
	private Integer version;
}
*/ 
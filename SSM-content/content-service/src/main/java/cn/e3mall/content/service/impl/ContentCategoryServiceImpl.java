package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 内容分类管理Service
 * <p>Title: ContentCategoryServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode>  treelist = new ArrayList<>();
		for(TbContentCategory category :list){
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(category.getId());
			treeNode.setText(category.getName());
			treeNode.setState(category.getIsParent()?"closed":"open");
			treelist.add(treeNode);
		}
		return treelist;
	}

	@Override
	public E3Result addContentCategory(long parentId, String name) {
		//创建一个tb_content_category表对应的pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		//设置pojo的属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//1(正常),2(删除)
		contentCategory.setStatus(1);
		//默认排序就是1
		contentCategory.setSortOrder(1);
		//新添加的节点一定是叶子节点
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//插入到数据库,数据库需要主键返回到contentCategory，因为最后返回了这个类
		contentCategoryMapper.insert(contentCategory);
		//判断父节点的isparent属性。如果不是true改为true
		//根据parentid查询父节点
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			//更新到数数据库
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回结果，返回E3Result，包含pojo
		return E3Result.ok(contentCategory);
	}

	/**
	 * 删除节点，使用递归删除。
	 * @param id
	 * @return
	 */
	@Override
	public E3Result deleteContentCategory(long id) {
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//判断是否为父节点
		if (category.getIsParent()){
			//如果是父节点，需将子节点递归删除
			List<TbContentCategory> list=new ArrayList<>();
			Stack<TbContentCategory> stack=new Stack<>();
			list.add(category);
			while (list.size() != 0){
				TbContentCategory contentCategory=list.remove(0);
				if (contentCategory.getIsParent()) {
					criteria.andParentIdEqualTo(contentCategory.getId());
					List<TbContentCategory> list1 = contentCategoryMapper.selectByExample(example);
					for (TbContentCategory t : list1) {
						list.add(t);
					}
				}
				stack.add(contentCategory);
			}
			while (stack.size() != 0){
				contentCategoryMapper.deleteByPrimaryKey(stack.pop().getId());
			}
			return E3Result.ok();
		}
		//不是父节点，可以直接删除，但是还得判断下改节点的父节点是否还有子节点，如果没有需设置setIsParent(false)
		else {
			contentCategoryMapper.deleteByPrimaryKey(id);
			criteria.andParentIdEqualTo(category.getParentId());
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
			if (list.size() == 0 ){
				TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(category.getParentId());
				parent.setIsParent(false);
				contentCategoryMapper.updateByPrimaryKey(parent);
			}
			return E3Result.ok();
		}

	}

}

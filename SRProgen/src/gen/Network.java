package gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Network {
	int arcs;// �����еı���(һ������Ŀ�е�)
	Project p;// ���ڱ�������ĸ�����,����Ŀ������

	public Network(Project p) {
		this.p = p;
	}

	/**
	 * ������Ŀ������
	 */
	boolean genNetWork(Demand demand, Utility utility) {
		int trialNr;// ��ʾ��ǰ�ǵڼ�������
		int pnr; // ��ǰ����Ŀ�ı��
		initNetw(0, p.nrOfJobs - 1);
		// ����ѭ������ÿ������Ŀ������
		boolean success = false; // ��ʾһ������Ŀ�������Ƿ����ɳɹ�
		for (pnr = 0; pnr < p.nrOfPro;) {
			trialNr = 1; // ��ʾ��ǰ���������(���ڵ�pnr������Ŀ����)
			arcs = 0;// ÿ������Ŀ(ÿ��������)��ʼ��������ʱ���������0
			success = false;
			while (!success && trialNr <= demand.base.maxTrials) {
				success = true;
				// ��������ֱ�ӽ�������Ŀ
				p.NSJ[pnr] = demand.base.minOutSour
						+ utility.random.nextInt(demand.base.maxOutSour
								- demand.base.minOutSour + 1);
				// β������ֱ�ӽ�ǰ�����Ŀ
				p.NFJ[pnr] = demand.base.minInSink
						+ utility.random.nextInt(demand.base.maxInSink
								- demand.base.minInSink + 1);
				arcs = p.NSJ[pnr] + p.NFJ[pnr];// ��ǰ����Ŀ�����еı���

				// ��ÿ���(�ų��鿪ʼ��ֱ�Ӻ��)���һ����ǰ�
				success = !utility.error(!(addPred(demand.base, pnr, utility)),
						1, demand.errFilePath, "");
				// ����Щ��û�к�̵����л���һ�����
				if (success)
					success = !utility.error(
							!(addSucc(demand.base, pnr, utility)), 2,
							demand.errFilePath, "");
				// ���縴�Ӷ�(NC)�Ķ��弴��(��)��Ŀ��ƽ��ÿ��������1��J��ӵ�еķ����໡����.
				if (success)
					// �����Ҫ�ĸ����Եͣ���NCԼΪ1���ܿ��ܻᷢ����ӵ������еĻ�����Ŀ̫��
					success = !utility.error(arcs > (1 + demand.base.netTol)
							* demand.base.compl * (p.pro[pnr] + 2), 4,
							demand.errFilePath, "");

				// ��Ӹ���Ļ���ֱ���ﵽԤ����NC��
				if (success)
					success = !utility.error(
							!(addArcsToCompl(demand.base, pnr, utility)), 3,
							demand.errFilePath, "");//

				//û�гɹ�����������
				if (!success) {
					trialNr++;
					initNetw(p.SJ[pnr], p.FJ[pnr]);
					arcs = 0;
				}
			}
			success = !utility.error(trialNr > demand.base.maxTrials, 1000,
					demand.errFilePath, "");
			if (success)
				pnr++;
			else
				// �ڹ涨��ʵ�������,û�����ɷ���Ҫ���ĳ������Ŀ������,��ʱ������Ŀ����������Ҳ��ʧ����
				return false;
		}
		//����������Ŀ��(������)��ʼ�(�ɶ��)���鿪ʼ�������������
		addArcsToSourceASink(demand.base);
		
		// ԭ�������滹Ҫ�����������Ƿ������໡���м��,Ϊʲô����������໡?==========================================
		if (!(this.noRedund())) {
			utility.error(true, 1001, demand.errFilePath, "");
			return false;
		}

		return true;// ������Ŀ���������ɳɹ�
	}

	/**
	 * ������������Ƿ������໡(��ô���������?)
	 * 
	 * @return
	 */
	private boolean noRedund() {
		for (int i = 0; i < this.p.nrOfJobs - 1; i++) {
			for (int j = i + 1; j < this.p.nrOfJobs; j++) {
				if (this.p.jobs[i].dirSucc.contains(j)) {
					for (int k = i + 1; k < j; k++) {
						if (this.p.jobs[i].dirSucc.contains(k)) {
							if (this.p.jobs[k].inDirSucc.contains(j)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * ��ʼ����Ŀ��·,ÿ����Ľ�ǰ/������Ŀ����Ϊ0,(ֱ��/���)��ǰ/���󼯶�Ϊ��
	 * 
	 * @param begin
	 *            ��ʼ����
	 * @param end
	 *            ��������
	 */
	void initNetw(int begin, int end) {
		for (int i = begin; i <= end; i++) {
			p.jobs[i].nrOfPred = 0;
			p.jobs[i].nrOfSucc = 0;
			p.jobs[i].dirPred.clear();
			p.jobs[i].dirSucc.clear();
			p.jobs[i].inDirPred.clear();
			p.jobs[i].inDirSucc.clear();
		}
	}

	/**
	 * Ϊÿ���(����ǰNSJ[pnr]���,��Ϊ��Щ���Ϊ�鿪ʼ���ֱ�Ӻ��)���һ����ǰ�,
	 * ������������һֱ�ӽ�ǰ�������Ϊ������Ľ�ǰ�
	 * 
	 * @param base
	 * @param pnr
	 *            ��ǰ����Ŀ���
	 * @param arcs
	 *            �����б���
	 * @param utility
	 * @return
	 */
	private boolean addPred(BaseStruct base, int pnr, Utility utility) {
		int j, iMax, index;
		List predecessor = new ArrayList();

		for (j = p.SJ[pnr] + p.NSJ[pnr]; j <= p.FJ[pnr]; j++) {
			iMax = (j - 1) < (p.FJ[pnr] - p.NFJ[pnr]) ? (j - 1)
					: (p.FJ[pnr] - p.NFJ[pnr]);

			// ����Ӹ�����Ŀpnr�ĵ�һ���SJ[pnr]��iMax(С��j)��Ѱ�����п�����Ϊj�Ľ�ǰ���,��¼��predecessor��
			predecessor.clear();
			for (int i = p.SJ[pnr]; i <= iMax; i++) {
				// ����i�Ľ�����С�ڻ����������Ŀ,��������ϻ�(i,j),�����������
				if ((p.jobs[i].nrOfSucc < base.maxOut)
						&& !(redundant(i, j, p.FJ[pnr]))) {
					predecessor.add(i);
				}
			}
			if (predecessor.size() > 0) {
				index = utility.random.nextInt(predecessor.size());
				addArc((Integer) predecessor.get(index), j);
			} else
				return false;// ����һ�û�к��ʿ�ѡ�Ľ�ǰ�ʱ,��ʧ��.�˴�����ʧ��
		}
		return true;
	}

	private boolean addSucc(BaseStruct base, int pnr, Utility utility) {
		int jMin, index;
		List successor = new ArrayList();

		for (int i = p.SJ[pnr]; i <= p.FJ[pnr] - p.NFJ[pnr]; i++) {
			if (p.jobs[i].nrOfSucc == 0) {
				jMin = (i + 1) > (p.SJ[pnr] + p.NSJ[pnr]) ? (i + 1)
						: (p.SJ[pnr] + p.NSJ[pnr]);
				// �����jMin��������Ŀ���һ�����Ѱ�����п���Ϊi�Ľ�����,��¼��successor��
				successor.clear();
				for (int j = jMin; j <= p.FJ[pnr]; j++) {
					if ((p.jobs[j].nrOfPred < base.maxIn)
							&& !(redundant(i, j, p.FJ[pnr])))
						successor.add(j);
				}

				if (successor.size() > 0) {
					index = utility.random.nextInt(successor.size());
					addArc(i, (Integer) successor.get(index));
				} else
					return false;
			}
		}
		return true;
	}

	/**
	 * �������в�����ӱ�,ֱ���ﵽҪ������縴�Ӷ�
	 * 
	 * @param base
	 * @param pnr
	 * @param utility
	 * @return
	 */
	private boolean addArcsToCompl(BaseStruct base, int pnr, Utility utility) {
		int root;// ���ѡ���һ���,��Ϊ����ӱߵ���ʼ�
		List successor = new ArrayList();
		int trialNr; // ʵ��Ĵ���
		int jMin, index;
		// ==========================================����ѭ�����ж������о����ԸĽ�(���Ķ�ԭ�ļ���ע�͹���)
		for (trialNr = 0; (arcs < base.compl * (p.pro[pnr] + 2))
				&& (trialNr <= base.maxTrials);) {
			root = p.SJ[pnr]
					+ utility.random.nextInt((p.FJ[pnr] - p.NFJ[pnr])
							- p.SJ[pnr] + 1);
			// ���ϵ�Ϊ���ѡ��Ļ��ӽ���
			if (p.jobs[root].nrOfSucc < base.maxOut) {
				jMin = (root + 1) > (p.SJ[pnr] + p.NSJ[pnr]) ? (root + 1)
						: (p.SJ[pnr] + p.NSJ[pnr]);
				successor.clear();
				for (int j = jMin; j <= p.FJ[pnr]; j++) {
					if ((p.jobs[j].nrOfPred < base.maxIn)
							&& !(redundant(root, j, p.FJ[pnr])))
						successor.add(j);
				}
				if (successor.size() > 0) {
					index = utility.random.nextInt(successor.size());
					addArc(root, (Integer) successor.get(index));
				} else
					trialNr++;
			} else
				trialNr++;
		}

		// ������Ӹ���Ļ���ֱ���ﵽԤ����NC��һ��׼������ĸ��������Ի�á�
		// �����һ�����޵ĳ������ѡ��һ���ڵ㲢������ܵĺ����ʱ��
		// û�и���Ļ��������ʹ��arcs>=J*NC*(1-��)ʱ��������Ϊ�ﵽ��׼��
		return (trialNr <= base.maxTrials)
				&& (arcs >= base.compl * (1 - base.netTol) * (p.pro[pnr] + 2));
	}

	/**
	 * Ϊ����������Ŀ��(������)��ʼ�(�ɶ��)���鿪ʼ�����,β�������������
	 * 
	 * @param base
	 */
	void addArcsToSourceASink(BaseStruct base) {
		for (int pnr = 0; pnr < p.nrOfPro; pnr++) {
			for (int i = 0; i < p.NSJ[pnr]; i++)
				addArc(0, p.SJ[pnr] + i);// ��һ������Ŀ�Ǵӻ1��ʼ��,�0Ϊ���л���鿪ʼ�
			for (int j = 0; j < p.NFJ[pnr]; j++)
				addArc(p.FJ[pnr] - j, p.nrOfJobs - 1);// ע��β���ı����p.nrOfJobs-1
		}
	}

	/**
	 * �ж�arc(i,j)�Ƿ��Ƕ����(����iС��j)
	 * 
	 * @param i
	 * @param j
	 * @param FJ
	 * @return
	 */
	boolean redundant(int i, int j, int FJ) {
		int k;
		Iterator iter;
		// case(a)���j������i�ĺ�����У���i��j�����໡
		if (p.jobs[i].inDirSucc.contains(j))
			return true;
		// case(b)���k������j�ĺ�����У�����i�Ľ�ǰ�����k��ֱ�ӽ�ǰ�����i��j�����໡
		for (k = j + 1; k <= FJ; k++) {
			if (p.jobs[j].inDirSucc.contains(k)) {
				iter = p.jobs[k].dirPred.iterator();
				while (iter.hasNext()) {
					if (p.jobs[i].inDirPred.contains(iter.next()))
						return true;
				}
			}
		}
		// case(c)���i�Ľ�ǰ�����j��ֱ�ӽ�ǰ�����i��j�����໡
		iter = p.jobs[j].dirPred.iterator();
		while (iter.hasNext()) {
			if (p.jobs[i].inDirPred.contains(iter.next()))
				return true;
		}
		// case(d)���j�Ľ�������i��ֱ�ӽ�������i��j�����໡
		iter = p.jobs[i].dirSucc.iterator();
		while (iter.hasNext()) {
			if (p.jobs[j].inDirSucc.contains(iter.next()))
				return true;
		}
		return false;
	}

	/**
	 * ���һ��(i,j)��
	 * 
	 * @param i
	 * @param j
	 * @param arcs
	 */
	void addArc(int i, int j) {
		p.jobs[i].nrOfSucc++;// i��ֱ�Ӻ����Ŀ��1
		p.jobs[i].dirSucc.add(j);
		p.jobs[i].inDirSucc.add(j);
		Iterator iter = p.jobs[j].inDirSucc.iterator();
		// ��j�Ľ������ϼ��뵽i�Ľ���������
		while (iter.hasNext()) {
			p.jobs[i].inDirSucc.add(iter.next());
		}

		p.jobs[j].nrOfPred++; // j��ֱ�ӽ�ǰ��Ŀ��1
		p.jobs[j].dirPred.add(i);
		p.jobs[j].inDirPred.add(i);
		iter = p.jobs[i].inDirPred.iterator();
		// ��i�Ľ�ǰ����ϼ��뵽j�Ľ�ǰ�������
		while (iter.hasNext()) {
			p.jobs[j].inDirPred.add(iter.next());
		}

		arcs++;// ���������1

		// ���³�ʼ��i�����н�ǰ��Ľ���
		for (int k = 0; k < i; k++) { // ����ط���k=0��ʼ�Ƿ����?����������л����Ϊ0�ļ�Ӻ��?
			if (p.jobs[i].inDirPred.contains(k)) {
				iter = p.jobs[i].inDirSucc.iterator();
				while (iter.hasNext())
					p.jobs[k].inDirSucc.add(iter.next());
			}
		}

		// ���³�ʼ��j�����н����Ľ�ǰ�
		for (int k = j + 1; k < p.nrOfJobs; k++) {// ����ط���k=p.nrOfJobs-1�Ƿ����?����������л����Ϊp.nrOfJobs�ļ��ǰ��==========================================
			if (p.jobs[j].inDirSucc.contains(k)) {
				iter = p.jobs[j].inDirPred.iterator();
				while (iter.hasNext())
					p.jobs[k].inDirPred.add(iter.next());
			}
		}

	}

}
